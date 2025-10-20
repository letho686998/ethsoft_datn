package com.ethsoft.mydatn.util;

import java.text.DecimalFormat;

/**
 * ✅ CodeGenerator
 * Class tiện ích sinh mã tự động cho sản phẩm, sản phẩm chi tiết, v.v.
 * Có thể mở rộng dùng chung cho các module khác (hóa đơn, khách hàng,...)
 */
public class CodeGenerator {

    /**
     * Sinh mã sản phẩm tiếp theo dạng SP000001, SP000002, ...
     */
    public static String nextSanPhamCode(String maxCode) {
        int next = 1;
        if (maxCode != null && maxCode.matches("SP\\d+")) {
            next = Integer.parseInt(maxCode.substring(2)) + 1;
        }
        return "SP" + new DecimalFormat("000000").format(next);
    }

    /**
     * Sinh mã sản phẩm chi tiết theo sản phẩm, màu và kích cỡ
     */
    public static String buildMaSPCT(String maSP, Long mauId, Long sizeId) {
        return maSP + "-M" + mauId + "-S" + sizeId;
        /* Ví dụ: SP000123-M1-S42*/
    }

//    private String buildMaSPCT(String maSP, String tenMau, String tenKichCo) {
//        // Rút gọn màu “Đỏ tươi” -> “DO”
//        String mauCode = tenMau.replaceAll("[^A-Z0-9]", "").toUpperCase();
//        if (mauCode.length() > 2) mauCode = mauCode.substring(0, 2);
//        return maSP + "-" + mauCode + "-" + tenKichCo;
//        /* Ví dụ: SP000123-DO-42*/
//    }

}
