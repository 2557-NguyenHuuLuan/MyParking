import 'package:flutter/material.dart';
import 'package:flutter_dotenv/flutter_dotenv.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';

class ContractApi {
  static final String baseUrl = dotenv.env['BASE_URL']!;
  static final FlutterSecureStorage _storage = FlutterSecureStorage();

  static Future<List<dynamic>> getUserVehicles() async {
    String? token = await _storage.read(key: "token");
    final userId = await _storage.read(key: "userId");

    final response = await http.get(
      Uri.parse("$baseUrl/api/vehicle/my-vehicles"),
      headers: {"Authorization": "Bearer $token"},
    );

    if (response.statusCode == 200) {
      return jsonDecode(response.body);
    } else {
      throw Exception("Lỗi khi lấy danh sách xe");
    }
  }

  /// Lấy danh sách gói thuê
  static Future<List<dynamic>> getRentalPackages() async {
    String? token = await _storage.read(key: "token");

    final response = await http.get(
      Uri.parse("$baseUrl/api/rental/all-packages"),
      headers: {"Authorization": "Bearer $token"},
    );

    if (response.statusCode == 200) {
      return jsonDecode(response.body);
    } else {
      throw Exception("Lỗi khi lấy danh sách gói thuê");
    }
  }

  static Future<List<dynamic>> getValidParkingSpots(String token, String date, String time, String packageId) async {
    final response = await http.get(
      Uri.parse("$baseUrl/api/parking-spots/parking-spots-valid?date=$date&time=$time&packageId=$packageId"),
      headers: {"Authorization": "Bearer $token"},
    );
    if (response.statusCode == 200) {
      return jsonDecode(response.body);
    } else {
      throw Exception("Lỗi khi lấy danh sách ô đậu hợp lệ");
    }
  }

  /// Đặt chỗ đậu xe
  static Future<bool> bookParkingSpot(
      String vehicleId, String packageId, String spotId, DateTime date, TimeOfDay time) async {
    String? token = await _storage.read(key: "token");
    final userId = await _storage.read(key: "userId");

    if (token == null || userId == null) {
      print("❌ Lỗi: Token hoặc userId không tồn tại!");
      return false;
    }

    // Định dạng lại rentalStart và rentalStartTime
    String formattedDate = "${date.toIso8601String().split('T')[0]}"; // yyyy-MM-dd
    String formattedTime = "${time.hour.toString().padLeft(2, '0')}:${time.minute.toString().padLeft(2, '0')}:00"; // HH:mm:ss

    Map<String, dynamic> requestBody = {
      "userId": userId,
      "vehicleId": vehicleId,
      "rentalPackageId": packageId,
      "parkingSpotId": spotId,
      "rentalStart": formattedDate,
      "rentalStartTime": formattedTime
    };

    print("📤 Gửi request: ${jsonEncode(requestBody)}");

    final response = await http.post(
      Uri.parse("$baseUrl/api/bookings"),
      headers: {
        "Content-Type": "application/json",
        "Authorization": "Bearer $token",
      },
      body: jsonEncode(requestBody),
    );

    print("🔹 Server phản hồi: ${response.statusCode} - ${response.body}");

    return response.statusCode == 200;
  }
  static Future<String?> getToken() async {
    return await _storage.read(key: "token");
  }
}
