-- Verificar usuarios con sus roles
SELECT u.username, u.email, r.name as role_name, p.avatar
FROM users u
JOIN role r ON u.role_id = r.id
JOIN profile p ON u.profile_id = p.id;

-- Verificar productos con sus proveedores
SELECT pr.name as product_name, pr2.name as provider_name, pm.brand, pm.category
FROM product pr
JOIN provider pr2 ON pr.provider_id = pr2.id
JOIN product_metadata pm ON pr.product_metadata_id = pm.id;

-- Verificar pagos con información de clientes
SELECT p.holder_name, p.number, p.brand, u.username, pa.city, pa.country
FROM payment p
LEFT JOIN profile pf ON p.profile_id = pf.id
LEFT JOIN users u ON pf.id = u.profile_id
LEFT JOIN payment_address pa ON p.payment_address_id = pa.id;

-- Verificar sesiones activas
SELECT u.username, s.jti, s.created_at, s.expires_at
FROM session s
JOIN users u ON s.user_id = u.id
WHERE s.expires_at > NOW();