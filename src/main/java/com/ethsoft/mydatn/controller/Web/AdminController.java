package com.ethsoft.mydatn.controller.Web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin")
public class AdminController {

    // Trang chủ admin
    @GetMapping("")
    public ModelAndView home() {
        return new ModelAndView("admin/adminWeb/home");
    }

    // Trang quản lý sản phẩm

    // /admin/san-pham -> trang danh sách
    @GetMapping("/san-pham")
    public String sanPhamListPage() {
        return "admin/adminWeb/sanpham"; // khớp file sanpham.html của bạn
    }

    // /admin/san-pham/them-san-pham -> trang thêm/sửa
    @GetMapping("/san-pham/them-san-pham")
    public String sanPhamCreatePage() {
        return "admin/adminWeb/themsanpham"; // khớp file themsanpham.html
    }

    // /admin/san-pham/san-pham-chi-tiet?id={id} (bạn điều hướng khi click Sửa từ list)
    @GetMapping("/san-pham/san-pham-chi-tiet")
    public String sanPhamDetailPage(@RequestParam("id") Long id) {
        // Có thể đặt attribute vào Model nếu cần, ở đây template sẽ tự gọi API để lấy dữ liệu
        return "admin/adminWeb/sanphamchitiet";
    }
    // Trang quản lý danh mục
    @GetMapping("/danh-muc")
    public ModelAndView danhmuc() {
        return new ModelAndView("admin/adminWeb/danhmuc");
    }
    // Trang quản lý thương hiệu
    @GetMapping("/thuong-hieu")
    public ModelAndView thuonghieu() {
        return new ModelAndView("admin/adminWeb/thuonghieu");
    }
    // Trang quản lý chất liệu đế
    @GetMapping("/de-giay")
    public ModelAndView degiay() {
        return new ModelAndView("admin/adminWeb/degiay");
    }
    // Trang quản lý chất liệu vải
    @GetMapping("/chat-lieu")
    public ModelAndView chatlieu() {
        return new ModelAndView("admin/adminWeb/chatlieu");
    }
    // Trang quản lý chất màu sắc
    @GetMapping("/mau-sac")
    public ModelAndView mausac() {
        return new ModelAndView("admin/adminWeb/mausac");
    }
    // Trang quản lý chất kích cỡ
    @GetMapping("/kich-co")
    public ModelAndView kichco() {
        return new ModelAndView("admin/adminWeb/kichco");
    }
}