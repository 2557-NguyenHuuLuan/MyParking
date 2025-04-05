import 'package:flutter_dotenv/flutter_dotenv.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';

class AuthAPI {
  static final String baseUrl = dotenv.env['BASE_URL']!;
  static final Uri url = Uri.parse("$baseUrl/api/auth");

  static Future<Map<String, dynamic>> register(String username, String email, String password) async {
    try {
      final response = await http.post(
        Uri.parse('$url/register'),
        headers: {'Content-Type': 'application/json'},
        body: json.encode({
          'username': username,
          'email': email,
          'password': password,
        }),
      );

      print('Response body: ${response.body}'); // Kiểm tra dữ liệu API trả về

      return json.decode(response.body); // Giờ API đã trả về JSON hợp lệ
    } catch (e) {
      return {"success": false, "error": "Lỗi kết nối đến server!"};
    }
  }

  static Future<Map<String, dynamic>> login(String username, String password) async {
    try {
      final response = await http.post(
        Uri.parse('$url/login'),
        headers: {'Content-Type': 'application/json'},
        body: json.encode({
          'username': username,
          'password': password,
        }),
      );

      print('Response body: ${response.body}'); // Kiểm tra dữ liệu API trả về

      return json.decode(response.body); // Trả về JSON từ API
    } catch (e) {
      return {"success": false, "error": "Lỗi kết nối đến server!"};
    }
  }


}
