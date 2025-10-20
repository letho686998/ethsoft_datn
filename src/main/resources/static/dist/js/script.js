document.addEventListener("DOMContentLoaded", function () {
    document.getElementById("btn-add").addEventListener("click", async function () {
        // Lấy dữ liệu từ form
        const tenSanPham = document.getElementById("name-product").value;
        const trangThai = document.getElementById("trangThai").value;
        const errorMessage = document.getElementById("error-message"); // Lấy phần hiển thị lỗi

        // Xóa thông báo lỗi trước đó
        errorMessage.textContent = "";

        // Kiểm tra dữ liệu nhập vào
        if (!tenSanPham.trim()) {
            errorMessage.textContent = "Vui lòng nhập tên sản phẩm!";
            return;
        }

        // Tạo object dữ liệu gửi lên server
        const productData = {
            tenSanPham: tenSanPham,
            trangThai: trangThai
        };

        try {
            // Gửi dữ liệu lên server bằng Axios
            const response = await axios.post("/api/sanpham", productData);

            // Hiển thị thông báo thành công
            alert(response.data.message);

            // Reset form sau khi thêm thành công
            document.getElementById("btn-reset").click();
        } catch (error) {
            if (error.response && error.response.status === 400) {
                // Hiển thị lỗi từ API ngay dưới ô nhập
                errorMessage.textContent = error.response.data.message;
            } else {
                alert("Đã xảy ra lỗi. Vui lòng thử lại!");
            }
        }
    });
});
