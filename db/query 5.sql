SELECT c.model, c.price, sp.name, s.date, s.sale_price
FROM car c JOIN sale s ON c.serial_no = s.car_serial_no
JOIN salesperson sp ON sp.id = s.salesperson_id;