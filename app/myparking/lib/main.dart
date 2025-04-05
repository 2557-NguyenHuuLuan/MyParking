import 'package:flutter/material.dart';
import 'package:font_awesome_flutter/font_awesome_flutter.dart';
import 'package:google_fonts/google_fonts.dart';
import 'package:animated_text_kit/animated_text_kit.dart';
import 'screens/login_screen.dart';
import 'screens/signup_screen.dart';
import 'package:flutter_dotenv/flutter_dotenv.dart';
void main() async{
  await dotenv.load(fileName: ".env");
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      home: SplashScreen(),
    );
  }
}

class SplashScreen extends StatefulWidget {
  @override
  _SplashScreenState createState() => _SplashScreenState();
}

class _SplashScreenState extends State<SplashScreen> {
  @override
  void initState() {
    super.initState();
    Future.delayed(Duration(seconds: 3), () {
      Navigator.pushReplacement(
        context,
        MaterialPageRoute(builder: (context) => WelcomeScreen()),
      );
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.black,
      body: Center(
        child: AnimatedTextKit(
          animatedTexts: [
            FadeAnimatedText('Xin chào!', textStyle: _textStyle()),
          ],
          totalRepeatCount: 1,
          pause: Duration(milliseconds: 100),
        ),
      ),
    );
  }

  TextStyle _textStyle() {
    return GoogleFonts.poppins(
      fontSize: 32,
      fontWeight: FontWeight.bold,
      color: Colors.amber,
    );
  }
}

class WelcomeScreen extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.black,
      body: Column(
        children: [
          Expanded(
            flex: 3,
            child: Row(
              children: [
                Expanded(
                  flex: 1,
                  child: Stack(
                    alignment: Alignment.center,
                    children: [
                      // Hình vuông màu amber (lớp dưới cùng)
                      Container(
                        width: 200,
                        height: 200,
                        decoration: BoxDecoration(
                          color: Colors.amber,
                          borderRadius: BorderRadius.circular(20),
                        ),
                      ),
                      Container(
                        width: 180, // Nhỏ hơn hình vuông amber
                        height: 180,
                        decoration: BoxDecoration(
                          color: Colors.transparent, // Nền trong suốt
                          border: Border.all(color: Colors.black, width: 5), // Viền đen
                          borderRadius: BorderRadius.circular(15), // Bo góc
                        ),
                      ),
                      // Chữ "P" nằm trên cùng
                      const Text(
                        'P',
                        style: TextStyle(
                          fontSize: 100,
                          fontWeight: FontWeight.bold,
                          color: Colors.black,
                        ),
                      ),
                    ],
                  ),
                ),
                Expanded(
                  flex: 1,
                  child: Padding(
                    padding: const EdgeInsets.only(left: 15),
                    child: Align(
                      alignment: Alignment.centerLeft, // Căn lề trái
                      child: AnimatedTextKit(
                        animatedTexts: [
                          TypewriterAnimatedText(
                            'Chào mừng bạn đến với MyParking !',
                            textAlign: TextAlign.left, // Căn lề trái cho nội dung chữ
                            textStyle: GoogleFonts.poppins(
                              fontSize: 28,
                              fontWeight: FontWeight.bold,
                              color: Colors.white,
                            ),
                            speed: Duration(milliseconds: 100),
                          ),
                        ],
                        totalRepeatCount: 1,
                      ),
                    ),
                  ),
                ),
              ],
            ),
          ),
          SizedBox(height: 30),
          Expanded(
            flex: 2,
            child: Center(
              child: Padding(
                padding: const EdgeInsets.all(20.0),
                child: Column(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    ElevatedButton(
                      style: ElevatedButton.styleFrom(
                        backgroundColor: Colors.amber,
                        foregroundColor: Colors.black,
                        padding:
                        EdgeInsets.symmetric(horizontal: 150, vertical: 15),
                        shape: RoundedRectangleBorder(
                          borderRadius: BorderRadius.circular(30),
                        ),
                      ),
                      onPressed: () {
                        Navigator.push(
                          context,
                          MaterialPageRoute(builder: (context) => LoginScreen()),
                        );
                      },
                      child: const Text(
                        'Đăng nhập',
                        style: TextStyle(
                            fontSize: 18, fontWeight: FontWeight.bold),
                      ),
                    ),
                    SizedBox(height: 10),
                    OutlinedButton(
                      style: OutlinedButton.styleFrom(
                        foregroundColor: Colors.amber,
                        side: BorderSide(color: Colors.amber),
                        padding:
                        EdgeInsets.symmetric(horizontal: 150, vertical: 15),
                        shape: RoundedRectangleBorder(
                          borderRadius: BorderRadius.circular(30),
                        ),
                      ),
                      onPressed: () {
                        Navigator.push(
                          context,
                          MaterialPageRoute(builder: (context) => SignUpScreen()),
                        );
                      },
                      child: const Text(
                        'Đăng ký',
                        style: TextStyle(
                            fontSize: 18, fontWeight: FontWeight.bold),
                      ),
                    ),
                    SizedBox(height: 20),
                    const Text(
                      'Hoặc đăng nhập bằng',
                      style: TextStyle(color: Colors.white),
                    ),
                    SizedBox(height: 10),
                    Row(
                      mainAxisAlignment: MainAxisAlignment.center,
                      children: [
                        IconButton(
                          icon: const FaIcon(FontAwesomeIcons.google,
                              color: Colors.white),
                          onPressed: () {},
                        ),
                        SizedBox(width: 20),
                        IconButton(
                          icon: const FaIcon(FontAwesomeIcons.facebook,
                              color: Colors.white),
                          onPressed: () {},
                        ),
                      ],
                    )
                  ],
                ),
              ),
            ),
          )
        ],
      ),
    );
  }
}