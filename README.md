create table Brands
(
    brand_id    int unsigned auto_increment
        primary key,
    name        varchar(100)         not null,
    logo_url    varchar(255)         null,
    description text                 null,
    status      tinyint(1) default 1 null
)
    collate = utf8mb4_unicode_ci;

create table Categories
(
    category_id int unsigned auto_increment
        primary key,
    name        varchar(100)         not null,
    parent_id   int unsigned         null,
    description text                 null,
    slug        varchar(100)         null,
    status      tinyint(1) default 1 null,
    constraint slug
        unique (slug),
    constraint Categories_ibfk_1
        foreign key (parent_id) references Categories (category_id)
            on update cascade on delete set null
)
    collate = utf8mb4_unicode_ci;

create index parent_id
    on Categories (parent_id);

create table Coupons
(
    coupon_id           int unsigned auto_increment
        primary key,
    code                varchar(50)                         not null,
    type                enum ('percentage', 'fixed_amount') not null,
    value               decimal(15, 2) unsigned             not null,
    min_order_amount    decimal(15, 2) unsigned             null,
    max_discount_amount decimal(15, 2) unsigned             null,
    start_date          timestamp                           null,
    end_date            timestamp                           null,
    usage_limit         int unsigned                        null,
    used_count          int unsigned default '0'            null,
    status              tinyint(1)   default 1              null,
    constraint code
        unique (code)
)
    collate = utf8mb4_unicode_ci;

create table Payment_Methods
(
    method_id   int unsigned auto_increment
        primary key,
    name        varchar(100)                         not null,
    code        varchar(50)                          not null,
    description text                                 null,
    status      tinyint(1) default 1                 null comment '1: active, 0: inactive',
    created_at  timestamp  default CURRENT_TIMESTAMP null,
    updated_at  timestamp  default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    constraint code
        unique (code)
)
    collate = utf8mb4_unicode_ci;

create table Permissions
(
    permission_id int unsigned auto_increment
        primary key,
    name          varchar(100)                        not null,
    code_name     varchar(100)                        not null,
    description   text                                null,
    group_name    varchar(50)                         null,
    created_at    timestamp default CURRENT_TIMESTAMP null,
    constraint code_name
        unique (code_name),
    constraint name
        unique (name)
)
    collate = utf8mb4_unicode_ci;

create table Products
(
    product_id  int unsigned auto_increment
        primary key,
    category_id int unsigned                         null,
    brand_id    int unsigned                         null,
    name        varchar(255)                         not null,
    description text                                 null,
    price       decimal(15, 2) unsigned              not null,
    sale_price  decimal(15, 2) unsigned              null,
    thumbnail   varchar(255)                         null,
    slug        varchar(255)                         null,
    status      tinyint(1) default 1                 null comment '1: active, 0: inactive',
    created_at  timestamp  default CURRENT_TIMESTAMP null,
    updated_at  timestamp  default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    constraint slug
        unique (slug),
    constraint Products_ibfk_1
        foreign key (category_id) references Categories (category_id)
            on update cascade on delete set null,
    constraint Products_ibfk_2
        foreign key (brand_id) references Brands (brand_id)
            on update cascade on delete set null
)
    collate = utf8mb4_unicode_ci;

create table Product_Images
(
    image_id      int unsigned auto_increment
        primary key,
    product_id    int unsigned         null,
    image_url     varchar(255)         not null,
    is_primary    tinyint(1) default 0 null,
    display_order int        default 0 null,
    constraint Product_Images_ibfk_1
        foreign key (product_id) references Products (product_id)
            on update cascade on delete cascade
)
    collate = utf8mb4_unicode_ci;

create index product_id
    on Product_Images (product_id);

create table Product_Variants
(
    variant_id     int unsigned auto_increment
        primary key,
    product_id     int unsigned            null,
    size           varchar(20)             null comment 'S, M, L, XL, XXL',
    color          varchar(50)             null,
    sku            varchar(50)             null,
    price          decimal(15, 2) unsigned null,
    stock_quantity int        default 0    null,
    image_url      varchar(255)            null,
    status         tinyint(1) default 1    null,
    constraint sku
        unique (sku),
    constraint Product_Variants_ibfk_1
        foreign key (product_id) references Products (product_id)
            on update cascade on delete cascade
)
    collate = utf8mb4_unicode_ci;

create index product_id
    on Product_Variants (product_id);

create index brand_id
    on Products (brand_id);

create index category_id
    on Products (category_id);

create table Roles
(
    role_id     int unsigned auto_increment
        primary key,
    name        varchar(50)                         not null,
    description text                                null,
    created_at  timestamp default CURRENT_TIMESTAMP null,
    updated_at  timestamp default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    constraint name
        unique (name)
)
    collate = utf8mb4_unicode_ci;

create table Role_Permissions
(
    role_id       int unsigned                        not null,
    permission_id int unsigned                        not null,
    created_at    timestamp default CURRENT_TIMESTAMP null,
    primary key (role_id, permission_id),
    constraint Role_Permissions_ibfk_1
        foreign key (role_id) references Roles (role_id)
            on update cascade on delete cascade,
    constraint Role_Permissions_ibfk_2
        foreign key (permission_id) references Permissions (permission_id)
            on update cascade on delete cascade
)
    collate = utf8mb4_unicode_ci;

create index permission_id
    on Role_Permissions (permission_id);

create table Users
(
    user_id       int unsigned auto_increment
        primary key,
    username      varchar(50)                          not null,
    email         varchar(100)                         not null,
    password_hash varchar(255)                         not null,
    full_name     varchar(100)                         null,
    phone         varchar(20)                          null,
    avatar        varchar(255)                         null,
    last_login_at timestamp                            null,
    created_at    timestamp  default CURRENT_TIMESTAMP null,
    updated_at    timestamp  default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    status        tinyint(1) default 1                 null comment '1: active, 0: inactive, 2: banned',
    constraint email
        unique (email),
    constraint username
        unique (username)
)
    collate = utf8mb4_unicode_ci;

create table User_Addresses
(
    address_id   int unsigned auto_increment
        primary key,
    user_id      int unsigned         null,
    address_line text                 not null,
    city         varchar(100)         null,
    district     varchar(100)         null,
    ward         varchar(100)         null,
    is_default   tinyint(1) default 0 null,
    constraint User_Addresses_ibfk_1
        foreign key (user_id) references Users (user_id)
            on update cascade on delete cascade
)
    collate = utf8mb4_unicode_ci;

create table Orders
(
    order_id     int unsigned auto_increment
        primary key,
    user_id      int unsigned                                                                                  null,
    address_id   int unsigned                                                                                  null,
    total_amount decimal(15, 2) unsigned                                                                       not null,
    shipping_fee decimal(15, 2) unsigned                                             default 0.00              null,
    status       enum ('pending', 'confirmed', 'shipping', 'completed', 'cancelled') default 'pending'         null,
    created_at   timestamp                                                           default CURRENT_TIMESTAMP null,
    updated_at   timestamp                                                           default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    constraint Orders_ibfk_1
        foreign key (user_id) references Users (user_id)
            on update cascade on delete set null,
    constraint Orders_ibfk_2
        foreign key (address_id) references User_Addresses (address_id)
            on update cascade on delete set null
)
    collate = utf8mb4_unicode_ci;

create table Order_Coupons
(
    order_id        int unsigned            not null,
    coupon_id       int unsigned            not null,
    discount_amount decimal(15, 2) unsigned not null,
    primary key (order_id, coupon_id),
    constraint Order_Coupons_ibfk_1
        foreign key (order_id) references Orders (order_id)
            on update cascade on delete cascade,
    constraint Order_Coupons_ibfk_2
        foreign key (coupon_id) references Coupons (coupon_id)
            on update cascade on delete cascade
);

create index coupon_id
    on Order_Coupons (coupon_id);

create table Order_Items
(
    order_id    int unsigned            not null,
    variant_id  int unsigned            not null,
    quantity    int unsigned            not null,
    unit_price  decimal(15, 2) unsigned not null,
    total_price decimal(15, 2) unsigned not null,
    primary key (order_id, variant_id),
    constraint Order_Items_ibfk_1
        foreign key (order_id) references Orders (order_id)
            on update cascade on delete cascade,
    constraint Order_Items_ibfk_2
        foreign key (variant_id) references Product_Variants (variant_id)
            on update cascade on delete cascade
)
    collate = utf8mb4_unicode_ci;

create index variant_id
    on Order_Items (variant_id);

create index address_id
    on Orders (address_id);

create index user_id
    on Orders (user_id);

create table Payments
(
    payment_id       int unsigned auto_increment
        primary key,
    order_id         int unsigned                                                                  null,
    method_id        int unsigned                                                                  null,
    amount           decimal(15, 2) unsigned                                                       not null,
    transaction_code varchar(100)                                                                  null,
    payment_status   enum ('pending', 'completed', 'failed', 'refunded') default 'pending'         null,
    payment_data     json                                                                          null,
    created_at       timestamp                                           default CURRENT_TIMESTAMP null,
    updated_at       timestamp                                           default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    constraint Payments_ibfk_1
        foreign key (order_id) references Orders (order_id)
            on update cascade on delete cascade,
    constraint Payments_ibfk_2
        foreign key (method_id) references Payment_Methods (method_id)
            on update cascade on delete set null
)
    collate = utf8mb4_unicode_ci;

create table Payment_History
(
    history_id int unsigned auto_increment
        primary key,
    payment_id int unsigned                        null,
    status     varchar(20)                         not null,
    note       text                                null,
    created_at timestamp default CURRENT_TIMESTAMP null,
    constraint Payment_History_ibfk_1
        foreign key (payment_id) references Payments (payment_id)
            on update cascade on delete cascade
)
    collate = utf8mb4_unicode_ci;

create index payment_id
    on Payment_History (payment_id);

create index method_id
    on Payments (method_id);

create index order_id
    on Payments (order_id);

create index user_id
    on User_Addresses (user_id);

create table User_Permissions
(
    user_id       int unsigned                         not null,
    permission_id int unsigned                         not null,
    granted       tinyint(1) default 1                 null comment 'TRUE: cấp thêm quyền, FALSE: loại bỏ quyền',
    created_at    timestamp  default CURRENT_TIMESTAMP null,
    primary key (user_id, permission_id),
    constraint User_Permissions_ibfk_1
        foreign key (user_id) references Users (user_id)
            on update cascade on delete cascade,
    constraint User_Permissions_ibfk_2
        foreign key (permission_id) references Permissions (permission_id)
            on update cascade on delete cascade
)
    collate = utf8mb4_unicode_ci;

create index permission_id
    on User_Permissions (permission_id);

create table User_Roles
(
    user_id    int unsigned                        not null,
    role_id    int unsigned                        not null,
    created_at timestamp default CURRENT_TIMESTAMP null,
    primary key (user_id, role_id),
    constraint User_Roles_ibfk_1
        foreign key (user_id) references Users (user_id)
            on update cascade on delete cascade,
    constraint User_Roles_ibfk_2
        foreign key (role_id) references Roles (role_id)
            on update cascade on delete cascade
)
    collate = utf8mb4_unicode_ci;

create index role_id
    on User_Roles (role_id);

