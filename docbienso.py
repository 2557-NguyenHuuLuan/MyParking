import cv2
import oracledb
import torch
import easyocr
import numpy as np
import threading
import tkinter as tk
from tkinter import messagebox
from ultralytics import YOLO
from pyzbar.pyzbar import decode  # Thư viện đọc QR code

# Thông tin kết nối Oracle
dsn = "localhost:1521/ORCL21"
username = "C##MYPARKING"
password = "123456"

# Khởi tạo mô hình YOLOv8 và EasyOCR
model = YOLO("yolov8n.pt").to("cuda")
reader = easyocr.Reader(['en'])

# Hàm kiểm tra biển số trong database
def check_plate_in_db(card_number, detected_plate):
    try:
        conn = oracledb.connect(user=username, password=password, dsn=dsn)
        cursor = conn.cursor()

        query = """
        SELECT v.numberplate FROM CARD c
        JOIN CONTRACT ct ON c.contract_id = ct.id
        JOIN CONTRACT_DETAIL cd ON ct.id = cd.contract_id
        JOIN VEHICLE v ON cd.vehicle_id = v.id
        WHERE c.card_number = :card_number
        """
        cursor.execute(query, {"card_number": card_number})
        results = cursor.fetchall()  # Lấy tất cả biển số

        cursor.close()
        conn.close()

        # Normalize detected_plate
        detected_plate_cleaned = detected_plate.replace(" ", "").upper()

        for row in results:
            db_plate = row[0].replace(" ", "").upper()
            if db_plate == detected_plate_cleaned:
                return True  # Có biển số khớp

        return False  # Không khớp

    except Exception as e:
        messagebox.showerror("Lỗi", f"Lỗi kết nối: {str(e)}")
        return False


# Hàm nhận diện biển số bằng AI
def detect_plate():
    cap = cv2.VideoCapture(0)
    if not cap.isOpened():
        messagebox.showerror("Lỗi", "Không thể mở webcam!")
        return

    while True:
        ret, frame = cap.read()
        if not ret:
            break

        results = model(frame)  # Dự đoán bằng YOLO
        detected_plate = ""

        for result in results:
            boxes = result.boxes.xyxy

            for box in boxes:
                x1, y1, x2, y2 = map(int, box)
                roi = frame[y1:y2, x1:x2]

                if roi.size == 0:
                    continue

                gray_roi = cv2.cvtColor(roi, cv2.COLOR_BGR2GRAY)
                gray_roi = cv2.equalizeHist(gray_roi)

                text = reader.readtext(gray_roi, detail=0)
                detected_plate = " ".join(text).replace(".", "")

                print(f"Biển số xe nhận diện: {detected_plate}")

                cv2.rectangle(frame, (x1, y1), (x2, y2), (0, 255, 0), 2)
                cv2.putText(frame, detected_plate, (x1, y1 - 10),
                            cv2.FONT_HERSHEY_SIMPLEX, 0.8, (0, 255, 0), 2)

        cv2.imshow("License Plate Recognition", frame)

        entered_card = entry_card.get().strip()
        if entered_card and detected_plate:
            if check_plate_in_db(entered_card, detected_plate):
                label_result.config(text="✅ Mời bạn vào", fg="green")
                print("✅ Mời bạn vào")
                cap.release()
                cv2.destroyAllWindows()
                return

            else:
                label_result.config(text="❌ Không hợp lệ", fg="red")

        if cv2.waitKey(1) & 0xFF == ord('q'):
            break

    cap.release()
    cv2.destroyAllWindows()

# Hàm đọc QR code từ camera và điền số thẻ
def read_qr_code():
    cap = cv2.VideoCapture(0)
    if not cap.isOpened():
        messagebox.showerror("Lỗi", "Không thể mở webcam để đọc QR!")
        return

    while True:
        ret, frame = cap.read()
        if not ret:
            break

        decoded_objects = decode(frame)
        for obj in decoded_objects:
            qr_data = obj.data.decode("utf-8")
            print(f"QR Code phát hiện: {qr_data}")

            # Điền vào ô nhập số thẻ
            entry_card.delete(0, tk.END)
            entry_card.insert(0, qr_data)

            # Đóng webcam đọc QR, mở webcam nhận diện biển số
            cap.release()
            cv2.destroyAllWindows()
            threading.Thread(target=detect_plate).start()
            return

        cv2.imshow("Quét QR Code", frame)

        if cv2.waitKey(1) & 0xFF == ord('q'):
            break

    cap.release()
    cv2.destroyAllWindows()

# Giao diện Tkinter
root = tk.Tk()
root.title("Hệ thống kiểm tra biển số xe")
root.geometry("400x300")

tk.Label(root, text="Nhập số thẻ:", font=("Arial", 12)).pack(pady=10)
entry_card = tk.Entry(root, font=("Arial", 12))
entry_card.pack(pady=5)

# Nút quét QR
btn_qr = tk.Button(root, text="Quét QR Code", font=("Arial", 12),
                   command=lambda: threading.Thread(target=read_qr_code).start())
btn_qr.pack(pady=10)

# Nút mở camera thủ công
btn_start = tk.Button(root, text="Mở Camera Nhận Diện", font=("Arial", 12),
                      command=lambda: threading.Thread(target=detect_plate).start())
btn_start.pack(pady=10)

label_result = tk.Label(root, text="", font=("Arial", 14, "bold"))
label_result.pack(pady=20)

root.mainloop()
