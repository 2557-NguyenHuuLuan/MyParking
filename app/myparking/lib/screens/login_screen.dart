import 'package:flutter/material.dart';
import 'package:google_fonts/google_fonts.dart';
import 'package:animate_do/animate_do.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'signup_screen.dart';
import 'home_screen.dart';
import '../api/auth_api.dart';

class LoginScreen extends StatefulWidget {
  @override
  _LoginScreenState createState() => _LoginScreenState();
}

class _LoginScreenState extends State<LoginScreen> {
  final _usernameController = TextEditingController();
  final _passwordController = TextEditingController();
  final _storage = FlutterSecureStorage(); // Secure Storage

  bool _isLoading = false;

  Future<void> _handleLogin() async {
    setState(() {
      _isLoading = true;
    });

    String username = _usernameController.text.trim();
    String password = _passwordController.text.trim();

    if (username.isEmpty || password.isEmpty) {
      _showMessage("Vui lòng nhập đầy đủ thông tin!", false);
      setState(() {
        _isLoading = false;
      });
      return;
    }

    Map<String, dynamic> response = await AuthAPI.login(username, password);

    setState(() {
      _isLoading = false;
    });

    if (response["success"]) {
      String token = response["token"];
      await _storage.write(key: "token", value: token); // Lưu token vào storage
      _showMessage("Đăng nhập thành công!", true);
      Navigator.pushReplacement(context, MaterialPageRoute(builder: (_) => HomeScreen())); // Chuyển trang
    } else {
      _showMessage(response["error"], false);
    }
  }

  void _showMessage(String message, bool isSuccess) {
    ScaffoldMessenger.of(context).showSnackBar(
      SnackBar(
        content: Text(message, style: TextStyle(color: Colors.white)),
        backgroundColor: isSuccess ? Colors.green : Colors.red,
        duration: Duration(seconds: 2),
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.black,
      body: Center(
        child: SingleChildScrollView(
          padding: const EdgeInsets.all(20.0),
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              FadeInDown(
                child: Text(
                  'Chào mừng trở lại!',
                  style: GoogleFonts.poppins(
                    color: Colors.amber,
                    fontSize: 28,
                    fontWeight: FontWeight.bold,
                  ),
                ),
              ),
              const SizedBox(height: 20),
              FadeInLeft(
                child: TextField(
                  controller: _usernameController,
                  decoration: const InputDecoration(
                    labelText: 'Tên người dùng',
                    labelStyle: TextStyle(color: Colors.white),
                    prefixIcon: Icon(Icons.account_box, color: Colors.amber),
                    enabledBorder: OutlineInputBorder(
                      borderSide: BorderSide(color: Colors.white, width: 2.5),
                    ),
                    focusedBorder: OutlineInputBorder(
                      borderSide: BorderSide(color: Colors.amber, width: 3),
                    ),
                  ),
                  style: TextStyle(color: Colors.white),
                ),
              ),
              const SizedBox(height: 20),
              FadeInRight(
                child: TextField(
                  controller: _passwordController,
                  obscureText: true,
                  decoration: const InputDecoration(
                    labelText: 'Mật khẩu',
                    labelStyle: TextStyle(color: Colors.white),
                    prefixIcon: Icon(Icons.lock, color: Colors.amber),
                    enabledBorder: OutlineInputBorder(
                      borderSide: BorderSide(color: Colors.white, width: 2.5),
                    ),
                    focusedBorder: OutlineInputBorder(
                      borderSide: BorderSide(color: Colors.amber, width: 3),
                    ),
                  ),
                  style: TextStyle(color: Colors.white),
                ),
              ),
              const SizedBox(height: 20),
              ZoomIn(
                child: ElevatedButton(
                  style: ElevatedButton.styleFrom(
                    backgroundColor: Colors.amber,
                    foregroundColor: Colors.black,
                    padding: EdgeInsets.symmetric(horizontal: 150, vertical: 15),
                    shape: RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(30),
                    ),
                  ),
                  onPressed: _isLoading ? null : _handleLogin,
                  child: _isLoading
                      ? CircularProgressIndicator(color: Colors.black)
                      : const Text(
                    'Đăng nhập',
                    style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
                  ),
                ),
              ),
              const SizedBox(height: 20),
              FadeInUp(
                child: TextButton(
                  onPressed: () {
                    Navigator.push(
                      context,
                      MaterialPageRoute(builder: (context) => SignUpScreen()),
                    );
                  },
                  child: const Text(
                    'Bạn chưa có tài khoản? Đăng ký ngay',
                    style: TextStyle(color: Colors.amber, fontSize: 16),
                  ),
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
