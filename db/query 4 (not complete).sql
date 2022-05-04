DELIMITER $$
DROP PROCEDURE IF EXISTS cars_report $$
CREATE PROCEDURE cars_report()
BEGIN
	DECLARE totalPrice INT DEFAULT 0;
	SELECT c.serial_no AS car_serial_no, (c.price + sum(op.price)) AS total_price INTO totalPrice 
    FROM car c JOIN car_option op ON c.serial_no = op.car_serial_no 
    GROUP BY op.car_serial_no;
    
	SELECT c.model, c.manufacturer, c.price AS base_price, 
    o.option_name AS option_name, o.price AS option_price
    FROM car c JOIN car_option o ON c.serial_no = o.car_serial_no
    JOIN totalPrice tp ON tp.car_serial_no = c.serial_no;
END $$
DELIMITER ;

CALL cars_report();


SELECT c.model, c.manufacturer, c.price AS base_price, 
o.option_name AS option_name, o.price AS option_price
FROM car c JOIN car_option o ON c.serial_no = o.car_serial_no;


SELECT c.serial_no AS car_serial_no, (c.price + sum(op.price)) AS total_price
FROM car c JOIN car_option op ON c.serial_no = op.car_serial_no 
GROUP BY op.car_serial_no;
