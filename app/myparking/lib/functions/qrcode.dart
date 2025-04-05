import 'dart:io';
import 'package:barcode/barcode.dart';

void buildQrCode(
    String data, {
      String? filename,
      double? width,
      double? height,
      double? fontHeight,
    }) {
  final bc = Barcode.qrCode();

  final svg = bc.toSvg(
    data,
    width: width ?? 200,
    height: height ?? 200,
    fontHeight: fontHeight,
  );

  filename ??= 'qr-code';

  final dir = Directory('assets/qrcodes');
  if (!dir.existsSync()) {
    dir.createSync(recursive: true);
  }

  final path = '${dir.path}/$filename.svg';

  // Lưu file SVG
  File(path).writeAsStringSync(svg);
  print('QR code saved to: ${Directory.current.path}/$path');
}
