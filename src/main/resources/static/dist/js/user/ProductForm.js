import React, { useState } from "react";
import { addProduct } from "../services/productService"; // Gọi API từ service

const ProductForm = () => {
    const [product, setProduct] = useState({ tenSanPham: "", trangThai: "" });

    const handleChange = (e) => {
        setProduct({ ...product, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await addProduct(product);
            setProduct({ tenSanPham: "", trangThai: "" }); // Reset form sau khi thêm
        } catch (error) {
            console.error("Tên đã tồn tại vui lòng nhập tên khác:", error);
        }
    };

    return (
        <form onSubmit={handleSubmit}>
            <input
                type="text"
                name="tenSanPham"
                value={product.tenSanPham}
                onChange={handleChange}
                placeholder="Tên sản phẩm"
                required
            />
            <input
                type="text"
                name="trangThai"
                value={product.trangThai}
                onChange={handleChange}
                placeholder="Trạng thái"
                required
            />
            <button type="submit">Thêm sản phẩm</button>
        </form>
    );
};

export default ProductForm;
