import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'booking_screen.dart';
import 'login_screen.dart';

class HomeScreen extends StatelessWidget {
  final FlutterSecureStorage _storage = FlutterSecureStorage();

  Future<void> _logout(BuildContext context) async {
    await _storage.delete(key: "token");
    Navigator.pushReplacement(
      context,
      MaterialPageRoute(builder: (_) => LoginScreen()),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.black,
      appBar: AppBar(
        title: Text("Trang chủ", style: TextStyle(color: Colors.amber)),
        backgroundColor: Colors.black,
        elevation: 2,
        iconTheme: IconThemeData(color: Colors.amber),
      ),
      drawer: Drawer(
        backgroundColor: Colors.black,
        child: ListView(
          padding: EdgeInsets.zero,
          children: [
            const DrawerHeader(
              decoration: BoxDecoration(
                color: Colors.amber,
              ),
              child: Column(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  CircleAvatar(
                    radius: 35,
                    backgroundImage: AssetImage('assets/avatar.png'),
                  ),
                  SizedBox(height: 10),
                  Text(
                    "Tên Người Dùng",
                    style: TextStyle(color: Colors.black, fontSize: 18, fontWeight: FontWeight.bold),
                  ),
                ],
              ),
            ),
            ListTile(
              leading: Icon(Icons.home, color: Colors.white),
              title: Text("Trang chủ", style: TextStyle(color: Colors.white)),
              onTap: () => Navigator.pop(context),
            ),
            ListTile(
              leading: Icon(Icons.logout, color: Colors.red),
              title: Text("Đăng xuất", style: TextStyle(color: Colors.red)),
              onTap: () => _logout(context),
            ),
          ],
        ),
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: GridView.count(
          crossAxisCount: 2,
          crossAxisSpacing: 10,
          mainAxisSpacing: 10,
          children: [
            _buildCard(Icons.car_rental, "Đặt chỗ", onTap: () {
              Navigator.push(
                context,
                MaterialPageRoute(builder: (context) => BookingScreen()),
              );
            }),
            _buildCard(Icons.car_repair, "Đăng ký xe"),
            _buildCard(Icons.history, "Lịch sử"),
            _buildCard(Icons.payment, "Thanh toán"),
            _buildCard(Icons.settings, "Cài đặt"),
          ],
        ),
      ),
    );
  }

  Widget _buildCard(IconData icon, String title, {VoidCallback? onTap}) {
    return Card(
      color: Colors.white10,
      shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(15)),
      child: InkWell(
        onTap: onTap,
        borderRadius: BorderRadius.circular(15),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Icon(icon, size: 50, color: Colors.amber),
            SizedBox(height: 10),
            Text(title, style: TextStyle(color: Colors.white, fontSize: 16, fontWeight: FontWeight.bold)),
          ],
        ),
      ),
    );
  }
}
