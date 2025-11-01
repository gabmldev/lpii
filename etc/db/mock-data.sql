-- Insertar roles
INSERT INTO role (id, name, description) VALUES
('11111111-1111-1111-1111-111111111111', 'Admin', 'Administrador del sistema con acceso completo'),
('22222222-2222-2222-2222-222222222222', 'Proveedor', 'Proveedor de productos y servicios'),
('33333333-3333-3333-3333-333333333333', 'Tienda', 'Gestor de tienda online'),
('44444444-4444-4444-4444-444444444444', 'Cliente', 'Usuario final que compra productos');

-- Insertar profiles
INSERT INTO profile (id, avatar) VALUES
('55555555-5555-5555-5555-555555555555', 'https://example.com/avatars/admin.jpg'),
('66666666-6666-6666-6666-666666666666', 'https://example.com/avatars/proveedor1.jpg'),
('77777777-7777-7777-7777-777777777777', 'https://example.com/avatars/tienda1.jpg'),
('88888888-8888-8888-8888-888888888888', 'https://example.com/avatars/cliente1.jpg'),
('99999999-9999-9999-9999-999999999999', 'https://example.com/avatars/cliente2.jpg'),
('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'https://example.com/avatars/proveedor2.jpg');

-- Insertar usuarios (contraseñas hasheadas - ejemplo: 'password123')
INSERT INTO users (id, username, email, pwd, role_id, profile_id) VALUES
('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', 'admin', 'admin@empresa.com', '$2a$10$abc123def456ghi789jkl0', '11111111-1111-1111-1111-111111111111', '55555555-5555-5555-5555-555555555555'),
('cccccccc-cccc-cccc-cccc-cccccccccccc', 'proveedor1', 'techsupply@empresa.com', '$2a$10$abc123def456ghi789jkl0', '22222222-2222-2222-2222-222222222222', '66666666-6666-6666-6666-666666666666'),
('dddddddd-dddd-dddd-dddd-dddddddddddd', 'tienda1', 'tiendaonline@empresa.com', '$2a$10$abc123def456ghi789jkl0', '33333333-3333-3333-3333-333333333333', '77777777-7777-7777-7777-777777777777'),
('eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee', 'cliente1', 'maria.garcia@email.com', '$2a$10$abc123def456ghi789jkl0', '44444444-4444-4444-4444-444444444444', '88888888-8888-8888-8888-888888888888'),
('ffffffff-ffff-ffff-ffff-ffffffffffff', 'cliente2', 'carlos.lopez@email.com', '$2a$10$abc123def456ghi789jkl0', '44444444-4444-4444-4444-444444444444', '99999999-9999-9999-9999-999999999999'),
('gggggggg-gggg-gggg-gggg-gggggggggggg', 'proveedor2', 'electroshop@empresa.com', '$2a$10$abc123def456ghi789jkl0', '22222222-2222-2222-2222-222222222222', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa');

-- Insertar sessions
INSERT INTO session (id, jti, token, user_id, created_at, expires_at) VALUES
('hhhhhhhh-hhhh-hhhh-hhhh-hhhhhhhhhhhh', 'jti_abc123', 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...', 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', NOW(), NOW() + INTERVAL '24 hours'),
('iiiiiiii-iiii-iiii-iiii-iiiiiiiiiiii', 'jti_def456', 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...', 'eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee', NOW(), NOW() + INTERVAL '8 hours'),
('jjjjjjjj-jjjj-jjjj-jjjj-jjjjjjjjjjjj', 'jti_ghi789', 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...', 'dddddddd-dddd-dddd-dddd-dddddddddddd', NOW(), NOW() + INTERVAL '12 hours');

-- Insertar providers
INSERT INTO provider (id, area) VALUES
('kkkkkkkk-kkkk-kkkk-kkkk-kkkkkkkkkkkk', 'Tecnología y Electrónica'),
('llllllll-llll-llll-llll-llllllllllll', 'Electrodomésticos'),
('mmmmmmmm-mmmm-mmmm-mmmm-mmmmmmmmmmmm', 'Ropa y Accesorios');

-- Insertar product_metadata
INSERT INTO product_metadata (id, brand, category, has_stack, has_sell_state) VALUES
('nnnnnnnn-nnnn-nnnn-nnnn-nnnnnnnnnnnn', 'Samsung', 'Smartphones', true, true),
('oooooooo-oooo-oooo-oooo-oooooooooooo', 'Apple', 'Laptops', true, true),
('pppppppp-pppp-pppp-pppp-pppppppppppp', 'LG', 'Televisores', true, false),
('qqqqqqqq-qqqq-qqqq-qqqq-qqqqqqqqqqqq', 'Sony', 'Auriculares', false, true),
('rrrrrrrr-rrrr-rrrr-rrrr-rrrrrrrrrrrr', 'Nike', 'Zapatillas Deportivas', true, true),
('ssssssss-ssss-ssss-ssss-ssssssssssss', 'Adidas', 'Ropa Deportiva', true, false);

-- Insertar products
INSERT INTO product (id, name, description, product_metadata_id, provider_id, profile_id) VALUES
('tttttttt-tttt-tttt-tttt-tttttttttttt', 'Samsung Galaxy S23', 'Smartphone flagship con cámara de 200MP', 'nnnnnnnn-nnnn-nnnn-nnnn-nnnnnnnnnnnn', 'kkkkkkkk-kkkk-kkkk-kkkk-kkkkkkkkkkkk', '66666666-6666-6666-6666-666666666666'),
('uuuuuuuu-uuuu-uuuu-uuuu-uuuuuuuuuuuu', 'MacBook Pro 16"', 'Laptop profesional con chip M2 Pro', 'oooooooo-oooo-oooo-oooo-oooooooooooo', 'kkkkkkkk-kkkk-kkkk-kkkk-kkkkkkkkkkkk', '66666666-6666-6666-6666-666666666666'),
('vvvvvvvv-vvvv-vvvv-vvvv-vvvvvvvvvvvv', 'LG OLED 65"', 'Televisor OLED 4K con Dolby Vision', 'pppppppp-pppp-pppp-pppp-pppppppppppp', 'llllllll-llll-llll-llll-llllllllllll', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa'),
('wwwwwwww-wwww-wwww-wwww-wwwwwwwwwwww', 'Sony WH-1000XM4', 'Auriculares noise cancelling', 'qqqqqqqq-qqqq-qqqq-qqqq-qqqqqqqqqqqq', 'kkkkkkkk-kkkk-kkkk-kkkk-kkkkkkkkkkkk', '66666666-6666-6666-6666-666666666666'),
('xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx', 'Nike Air Max 270', 'Zapatillas deportivas con tecnología Air', 'rrrrrrrr-rrrr-rrrr-rrrr-rrrrrrrrrrrr', 'mmmmmmmm-mmmm-mmmm-mmmm-mmmmmmmmmmmm', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa'),
('yyyyyyyy-yyyy-yyyy-yyyy-yyyyyyyyyyyy', 'Adidas Ultraboost', 'Zapatillas running con Boost', 'ssssssss-ssss-ssss-ssss-ssssssssssss', 'mmmmmmmm-mmmm-mmmm-mmmm-mmmmmmmmmmmm', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa');

-- Insertar payment_address
INSERT INTO payment_address (id, street, city, state, postal_code, country) VALUES
('zzzzzzzz-zzzz-zzzz-zzzz-zzzzzzzzzzzz', 'Calle Principal 123', 'Madrid', 'Madrid', '28001', 'España'),
('11111111-1111-1111-1111-111111111112', 'Avenida Central 456', 'Barcelona', 'Cataluña', '08001', 'España'),
('11111111-1111-1111-1111-111111111113', 'Plaza Mayor 789', 'Valencia', 'Valencia', '46001', 'España'),
('11111111-1111-1111-1111-111111111114', 'Calle Secundaria 321', 'Sevilla', 'Andalucía', '41001', 'España');

-- Insertar payments
INSERT INTO payment (id, number, holder_name, expiry_month, expiry_year, cvv, brand, provider_id, profile_id, payment_address_id) VALUES
('11111111-1111-1111-1111-111111111115', '4111111111111111', 'MARIA GARCIA LOPEZ', 12, 2026, '123', 'Visa', 'kkkkkkkk-kkkk-kkkk-kkkk-kkkkkkkkkkkk', '88888888-8888-8888-8888-888888888888', 'zzzzzzzz-zzzz-zzzz-zzzz-zzzzzzzzzzzz'),
('11111111-1111-1111-1111-111111111116', '5555555555554444', 'CARLOS LOPEZ MARTINEZ', 8, 2025, '456', 'MasterCard', 'llllllll-llll-llll-llll-llllllllllll', '99999999-9999-9999-9999-999999999999', '11111111-1111-1111-1111-111111111112'),
('11111111-1111-1111-1111-111111111117', '378282246310005', 'TECH SUPPLY SL', 3, 2027, '789', 'American Express', NULL, '66666666-6666-6666-6666-666666666666', '11111111-1111-1111-1111-111111111113'),
('11111111-1111-1111-1111-111111111118', '6011111111111117', 'ELECTRO SHOP SA', 11, 2026, '321', 'Discover', NULL, 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', '11111111-1111-1111-1111-111111111114');

-- Insertar logs
INSERT INTO log (trigger, origin, payload, created_at) VALUES
('USER_LOGIN', 'AuthService', '{"user_id": "bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb", "ip": "192.168.1.100"}', NOW() - INTERVAL '2 hours'),
('PRODUCT_CREATED', 'ProductService', '{"product_id": "tttttttt-tttt-tttt-tttt-tttttttttttt", "name": "Samsung Galaxy S23"}', NOW() - INTERVAL '1 day'),
('PAYMENT_PROCESSED', 'PaymentService', '{"payment_id": "11111111-1111-1111-1111-111111111115", "amount": 899.99, "status": "completed"}', NOW() - INTERVAL '30 minutes'),
('USER_REGISTERED', 'AuthService', '{"user_id": "eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee", "email": "maria.garcia@email.com"}', NOW() - INTERVAL '5 days'),
('PRODUCT_UPDATED', 'ProductService', '{"product_id": "uuuuuuuu-uuuu-uuuu-uuuu-uuuuuuuuuuuu", "price": 2499.99}', NOW() - INTERVAL '3 hours');

-- Actualizar algunos updated_at para simular modificaciones
UPDATE role SET name = 'Administrador' WHERE id = '11111111-1111-1111-1111-111111111111';
UPDATE users SET username = 'admin_principal' WHERE id = 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb';
UPDATE product SET name = 'Samsung Galaxy S23 Ultra' WHERE id = 'tttttttt-tttt-tttt-tttt-tttttttttttt';
UPDATE provider SET area = 'Tecnología, Electrónica y Smart Home' WHERE id = 'kkkkkkkk-kkkk-kkkk-kkkk-kkkkkkkkkkkk';