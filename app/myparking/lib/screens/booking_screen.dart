import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:myparking/api/contract_api.dart';

class BookingScreen extends StatefulWidget {
  @override
  _BookingScreenState createState() => _BookingScreenState();
}

class _BookingScreenState extends State<BookingScreen> {
  List<dynamic> rentalPackages = [];
  List<dynamic> parkingSpots = [];
  List<dynamic> userVehicles = [];

  String? selectedPackage;
  String? selectedSpot;
  String? selectedVehicle;

  DateTime selectedDate = DateTime.now();
  TimeOfDay selectedTime = TimeOfDay.now();

  bool isLoading = false;

  @override
  void initState() {
    super.initState();
    _loadInitialData();
  }

  Future<void> _loadInitialData() async {
    await Future.wait([
      _loadRentalPackages(),
      _loadUserVehicles(),
    ]);
    _loadParkingSpots();
  }

  Future<void> _loadRentalPackages() async {
    try {
      var packages = await ContractApi.getRentalPackages();
      setState(() => rentalPackages = packages ?? []);
    } catch (e) {
      print("Lỗi tải gói thuê: $e");
    }
  }

  Future<void> _loadUserVehicles() async {
    try {
      var vehicles = await ContractApi.getUserVehicles();
      setState(() => userVehicles = vehicles ?? []);
    } catch (e) {
      print("Lỗi tải xe: $e");
    }
  }

  String _formatTime(TimeOfDay time) {
    return "${time.hour.toString().padLeft(2, '0')}:${time.minute.toString().padLeft(2, '0')}";
  }

  Future<void> _loadParkingSpots() async {
    if (selectedPackage == null) return;
    setState(() => isLoading = true);

    try {
      String? token = await ContractApi.getToken();
      if (token == null) throw Exception("Không tìm thấy token, đăng nhập lại.");

      var formattedTime = _formatTime(selectedTime);

      print("Gọi API với các tham số: ");
      print("Ngày: ${selectedDate.toIso8601String().split('T')[0]}");
      print("Giờ: $formattedTime");
      print("Gói thuê: $selectedPackage");

      var spotsData = await ContractApi.getValidParkingSpots(
        token,
        selectedDate.toIso8601String().split('T')[0],
        formattedTime,
        selectedPackage!,
      );

      print("Dữ liệu ô đậu từ server: $spotsData");

      // Lấy tất cả parking spots từ các zones
      List<dynamic> allParkingSpots = [];
      for (var zone in spotsData) {
        if (zone.containsKey("parkingSpots")) {
          for (var spot in zone["parkingSpots"]) {
            // Kiểm tra status của từng parkingSpot
            if (spot["status"] == 1) {
              allParkingSpots.add(spot);
            }
          }
        }
      }

      setState(() {
        parkingSpots = allParkingSpots;
      });
    } catch (e) {
      print("Lỗi tải ô đậu xe: $e");
    }

    setState(() => isLoading = false);
  }

  // Cập nhật hàm _selectDate khi người dùng chọn ngày
  Future<void> _selectDate(BuildContext context) async {
    final DateTime? picked = await showDatePicker(
      context: context,
      initialDate: selectedDate,
      firstDate: DateTime.now(),
      lastDate: DateTime(2101),
    );
    if (picked != null && picked != selectedDate) {
      setState(() => selectedDate = picked);
      _loadParkingSpots(); // Gọi lại khi chọn ngày
    }
  }

// Cập nhật hàm _selectTime khi người dùng chọn giờ
  Future<void> _selectTime(BuildContext context) async {
    final TimeOfDay? picked = await showTimePicker(
      context: context,
      initialTime: selectedTime,
    );
    if (picked != null && picked != selectedTime) {
      setState(() => selectedTime = picked);
      _loadParkingSpots(); // Gọi lại khi chọn giờ
    }
  }

  Future<void> _bookParking() async {
    if ([selectedPackage, selectedSpot, selectedVehicle].contains(null)) {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text("Vui lòng chọn đủ thông tin!")),
      );
      return;
    }

    setState(() => isLoading = true);

    try {
      print("🔹 Bắt đầu gửi yêu cầu đặt chỗ...");
      print("🚗 Xe: $selectedVehicle");
      print("📦 Gói thuê: $selectedPackage");
      print("🅿️ Ô đậu: $selectedSpot");
      print("📅 Ngày: ${selectedDate.toIso8601String().split('T')[0]}");
      print("🕒 Giờ: ${_formatTime(selectedTime)}");

      bool success = await ContractApi.bookParkingSpot(
        selectedVehicle!,
        selectedPackage!,
        selectedSpot!,
        selectedDate,
        selectedTime,
      );

      print("✅ Kết quả đặt chỗ: ${success ? "Thành công" : "Thất bại"}");

      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text(success ? "Đặt chỗ thành công!" : "Đặt chỗ thất bại!")),
      );
    } catch (e) {
      print("❌ Lỗi đặt chỗ: $e");

      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text("Lỗi đặt chỗ: $e")),
      );
    }

    setState(() => isLoading = false);
  }


  String _convertForm(String form) {
    switch (form) {
      case "DAY":
        return "ngày";
      case "MONTH":
        return "tháng";
      case "YEAR":
        return "năm";
      case "HOUR":
        return "giờ";
      default:
        return form;
    }
  }


  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text("Đặt chỗ đậu xe")),
      body: Padding(
        padding: EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            DropdownButtonFormField(
              decoration: InputDecoration(labelText: "Chọn gói thuê"),
              items: rentalPackages.map((package) {
                String formattedDuration = "${package["numberOf"]} ${_convertForm(package["rentalForm"]["form"])}";
                return DropdownMenuItem(
                  value: package["id"].toString(),
                  child: Text(formattedDuration),
                );
              }).toList(),
              onChanged: (value) {
                setState(() => selectedPackage = value as String?);
                _loadParkingSpots();
              },
            ),
            SizedBox(height: 10),
            Row(
              children: [
                Expanded(child: Text("Ngày: ${DateFormat('dd/MM/yyyy').format(selectedDate)}")),
                ElevatedButton(onPressed: () => _selectDate(context), child: Text("Chọn ngày")),
              ],
            ),
            Row(
              children: [
                Expanded(child: Text("Giờ: ${selectedTime.format(context)}")),
                ElevatedButton(onPressed: () => _selectTime(context), child: Text("Chọn giờ")),
              ],
            ),
            SizedBox(height: 10),
            if (isLoading) Center(child: CircularProgressIndicator()),
            SizedBox(height: 10),
            DropdownButtonFormField(
              decoration: InputDecoration(labelText: "Chọn ô đậu"),
              items: parkingSpots.isNotEmpty
                  ? parkingSpots.map((spot) {
                return DropdownMenuItem(
                  value: spot["id"].toString(),
                  child: Text(spot["location"] ?? "Không xác định"),
                );
              }).toList()
                  : [
                const DropdownMenuItem(
                  value: null,
                  child: Text("Không có ô đậu hợp lệ"),
                ),
              ],
              onChanged: (value) => setState(() => selectedSpot = value as String?),
            ),
            SizedBox(height: 10),
            DropdownButtonFormField(
              decoration: InputDecoration(labelText: "Chọn xe"),
              items: userVehicles.map((vehicle) {
                return DropdownMenuItem(
                  value: vehicle["id"].toString(),
                  child: Text(vehicle["numberPlate"] ?? "Không có biển số"),
                );
              }).toList(),
              onChanged: (value) => setState(() => selectedVehicle = value as String?),
            ),
            SizedBox(height: 20),
            ElevatedButton(
              onPressed: isLoading ? null : _bookParking,
              child: Text("Xác nhận đặt chỗ"),
            ),
          ],
        ),
      ),
    );
  }
}