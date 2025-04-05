import 'package:flutter_dotenv/flutter_dotenv.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
class UserAPI {
  static final String baseUrl = dotenv.env['BASE_URL']!;
  static final Uri url = Uri.parse("$baseUrl/api/user");
  static final FlutterSecureStorage _storage = FlutterSecureStorage();

  static Future<Map<String, dynamic>> getUserProfile() async {
    String? token = await _storage.read(key: "token");
    if (token == null) {
      return {"success": false, "error": "Token không tồn tại!"};
    }

    try {
      final response = await http.get(
        Uri.parse('$baseUrl/user/me'),
        headers: {
          'Content-Type': 'application/json',
          'Authorization': 'Bearer $token', // Gửi token lên server
        },
      );

      return json.decode(response.body);
    } catch (e) {
      return {"success": false, "error": "Lỗi kết nối server!"};
    }
  }
}