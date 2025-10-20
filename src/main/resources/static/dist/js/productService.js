import axios from 'axios';

export const addProduct = async (productData) => {
    try {
        const response = await axios.post('/api/sanpham', productData);
        alert('Thêm sản phẩm thành công!');
        return response.data; // Trả về dữ liệu để xử lý tiếp
    } catch (error) {
        if (error.response && error.response.status === 400) {
            alert(error.response.data.message); // Hiển thị lỗi từ backend
        } else {
            alert('Sản phẩm đã tn tại. Vui lòng thử lại!');
        }
        throw error; // Ném lỗi để component xử lý nếu cần
    }
};
