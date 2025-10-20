-- san_pham
CREATE TABLE san_pham (
                          id BIGINT IDENTITY PRIMARY KEY,
                          ma_san_pham VARCHAR(50) UNIQUE,
                          ten_san_pham NVARCHAR(255) NOT NULL,
                          mo_ta NVARCHAR(MAX),
                          trang_thai INT,
                          thuong_hieu_id BIGINT,
                          danh_muc_id BIGINT,
                          chat_lieu_id BIGINT,
                          de_giay_id BIGINT,
                          gioi_tinh INT,
                          nguoi_tao NVARCHAR(100),
                          nguoi_cap_nhat NVARCHAR(100),
                          ngay_tao DATETIME2,
                          ngay_cap_nhat DATETIME2
);

-- san_pham_chi_tiet
CREATE TABLE san_pham_chi_tiet (
                                   id BIGINT IDENTITY PRIMARY KEY,
                                   ma_spct VARCHAR(100) UNIQUE,
                                   san_pham_id BIGINT NOT NULL,
                                   mau_sac_id BIGINT,
                                   kich_co_id BIGINT,
                                   so_luong_ton INT,
                                   gia_nhap DECIMAL(18,2),
                                   gia_ban DECIMAL(18,2),
                                   trang_thai INT,
                                   nguoi_tao NVARCHAR(100),
                                   nguoi_cap_nhat NVARCHAR(100),
                                   ngay_tao DATETIME2,
                                   ngay_cap_nhat DATETIME2,
                                   CONSTRAINT fk_spct_sp FOREIGN KEY (san_pham_id) REFERENCES san_pham(id)
);

-- san_pham_mau_anh
CREATE TABLE san_pham_mau_anh (
                                  id BIGINT IDENTITY PRIMARY KEY,
                                  san_pham_id BIGINT NOT NULL,
                                  mau_sac_id BIGINT,
                                  duong_dan NVARCHAR(500) NOT NULL,
                                  mo_ta NVARCHAR(255),
                                  la_anh_bia BIT NOT NULL,
                                  thu_tu INT NOT NULL,
                                  nguon VARCHAR(50),
                                  ngay_tao DATETIME2 NOT NULL DEFAULT SYSDATETIME(),
                                  CONSTRAINT fk_spma_sp FOREIGN KEY (san_pham_id) REFERENCES san_pham(id)
);

CREATE TABLE thuong_hieu (
                             id BIGINT IDENTITY PRIMARY KEY,
                             ten_thuong_hieu NVARCHAR(255) UNIQUE NOT NULL,
                             trang_thai INT NOT NULL DEFAULT 1,
                             nguoi_tao NVARCHAR(100),
                             nguoi_cap_nhat NVARCHAR(100),
                             ngay_tao DATETIME2,
                             ngay_cap_nhat DATETIME2
);

CREATE TABLE danh_muc (
                          id BIGINT IDENTITY PRIMARY KEY,
                          ten_danh_muc NVARCHAR(255) UNIQUE NOT NULL,
                          trang_thai INT NOT NULL DEFAULT 1,
                          nguoi_tao NVARCHAR(100),
                          nguoi_cap_nhat NVARCHAR(100),
                          ngay_tao DATETIME2,
                          ngay_cap_nhat DATETIME2
);

CREATE TABLE chat_lieu (
                           id BIGINT IDENTITY PRIMARY KEY,
                           ten_chat_lieu NVARCHAR(255) UNIQUE NOT NULL,
                           trang_thai INT NOT NULL DEFAULT 1,
                           nguoi_tao NVARCHAR(100),
                           nguoi_cap_nhat NVARCHAR(100),
                           ngay_tao DATETIME2,
                           ngay_cap_nhat DATETIME2
);

CREATE TABLE de_giay (
                         id BIGINT IDENTITY PRIMARY KEY,
                         ten_de_giay NVARCHAR(255) UNIQUE NOT NULL,
                         trang_thai INT NOT NULL DEFAULT 1,
                         nguoi_tao NVARCHAR(100),
                         nguoi_cap_nhat NVARCHAR(100),
                         ngay_tao DATETIME2,
                         ngay_cap_nhat DATETIME2
);

CREATE TABLE mau_sac (
                         id BIGINT IDENTITY(1,1) PRIMARY KEY,
                         ten_mau NVARCHAR(100) NOT NULL UNIQUE,
                         ma_mau VARCHAR(20) NOT NULL UNIQUE,
                         trang_thai INT NOT NULL DEFAULT 1,
                         nguoi_tao NVARCHAR(100),
                         nguoi_cap_nhat NVARCHAR(100),
                         ngay_tao DATETIME2 DEFAULT SYSDATETIME(),
                         ngay_cap_nhat DATETIME2 DEFAULT SYSDATETIME()
);

CREATE TABLE kich_co (
                         id BIGINT IDENTITY(1,1) PRIMARY KEY,
                         ten_kich_co NVARCHAR(50) NOT NULL UNIQUE,
                         trang_thai INT NOT NULL DEFAULT 1,
                         nguoi_tao NVARCHAR(100),
                         nguoi_cap_nhat NVARCHAR(100),
                         ngay_tao DATETIME2 DEFAULT SYSDATETIME(),
                         ngay_cap_nhat DATETIME2 DEFAULT SYSDATETIME()
);
