package com.ethsoft.mydatn.util;

import java.text.DecimalFormat;
import java.text.Normalizer;

/**
 * ✅ CodeGenerator
 * Class tiện ích sinh mã tự động cho sản phẩm, sản phẩm chi tiết, v.v.
 * Có thể mở rộng dùng chung cho các module khác (hóa đơn, khách hàng,...)
 */
public class CodeGenerator {

    /**
     * Sinh mã sản phẩm tiếp theo dạng SP0001, SP0002, ...
     */
    public static String nextSanPhamCode(String maxCode) {
        int next = 1;
        if (maxCode != null && maxCode.matches("SP\\d+")) {
            next = Integer.parseInt(maxCode.substring(2)) + 1;
        }
        return "SP" + new DecimalFormat("0000").format(next);
    }

    /**
     * Sinh mã sản phẩm chi tiết theo sản phẩm, màu và kích cỡ
     */
//    public static String buildMaSPCT(String maSP, Long mauId, Long sizeId) {
//        return maSP + "-M" + mauId + "-S" + sizeId;
//        /* Ví dụ: SP000123-M1-S42*/
//    }

    public static String buildMaSPCT(String maSP, String tenMau, String tenKichCo) {
        // Rút gọn màu “Đỏ tươi” -> “DO”
        if (tenMau == null || tenMau.isBlank()) {
            return maSP + "-XX-" + tenKichCo;
        }

        // 1️⃣ Chuẩn hóa riêng các ký tự Đ/đ
        tenMau = tenMau.replace('Đ', 'D').replace('đ', 'd');

        // 2️⃣ Bỏ dấu tiếng Việt
        String normalized = Normalizer.normalize(tenMau, Normalizer.Form.NFD);
        String mauKhongDau = normalized.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");

        // 3️⃣ Chuyển in hoa
        mauKhongDau = mauKhongDau.toUpperCase().trim();

        // 4️⃣ Lấy 2 chữ cái đầu (ưu tiên mỗi từ đầu tiên nếu có 2 từ)
        String mauCode;
        String[] parts = mauKhongDau.split("\\s+");
        if (parts.length >= 2) {
            mauCode = "" + parts[0].charAt(0) + parts[1].charAt(0);
        } else {
            mauCode = parts[0].substring(0, Math.min(2, parts[0].length()));
        }

        // 5️⃣ Xóa ký tự không hợp lệ
        mauCode = mauCode.replaceAll("[^A-Z0-9]", "");

        // 6️⃣ Nếu vẫn trống, đặt mặc định
        if (mauCode.isEmpty()) mauCode = "XX";

        return maSP + "-" + mauCode + "-" + tenKichCo;
        /* Ví dụ: SP000123-DO-42*/
    }

}
