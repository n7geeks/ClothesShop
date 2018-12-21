-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Dec 21, 2018 at 10:01 AM
-- Server version: 5.7.19
-- PHP Version: 7.1.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `clothes_shop`
--

-- --------------------------------------------------------

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
CREATE TABLE IF NOT EXISTS `categories` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(128) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `categories`
--

INSERT INTO `categories` (`id`, `title`) VALUES
(2, 'Jackets');

-- --------------------------------------------------------

--
-- Table structure for table `customers`
--

DROP TABLE IF EXISTS `customers`;
CREATE TABLE IF NOT EXISTS `customers` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) NOT NULL,
  `firstName` varchar(255) NOT NULL,
  `lastName` varchar(255) NOT NULL,
  `phone` varchar(255) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `customers`
--

INSERT INTO `customers` (`id`, `code`, `firstName`, `lastName`, `phone`, `address`, `email`) VALUES
(2, 'EE77777', 'Mohamed', 'BOUKHLIF', '06666666', '28 DB Lamhrami SYBA Marrakech', 'boukhlifmohamed13@gmail.com');

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
CREATE TABLE IF NOT EXISTS `orders` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `customer_id` bigint(20) NOT NULL,
  `total` double(8,2) NOT NULL,
  `created_at` timestamp NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_orders_customers` (`customer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `orders`
--

INSERT INTO `orders` (`id`, `customer_id`, `total`, `created_at`) VALUES
(1, 2, 800.00, '2018-12-05 14:28:22'),
(2, 2, 1200.00, '2018-12-05 15:11:33'),
(3, 2, 400.00, '2018-12-16 21:51:51'),
(4, 2, 400.00, '2018-12-21 09:50:33');

-- --------------------------------------------------------

--
-- Table structure for table `order_lines`
--

DROP TABLE IF EXISTS `order_lines`;
CREATE TABLE IF NOT EXISTS `order_lines` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `product_id` bigint(20) NOT NULL,
  `order_id` bigint(20) NOT NULL,
  `qty` int(11) NOT NULL,
  `price` double(8,2) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_order_lines_products` (`product_id`),
  KEY `fk_order_lines_orders` (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `order_lines`
--

INSERT INTO `order_lines` (`id`, `product_id`, `order_id`, `qty`, `price`) VALUES
(1, 2, 1, 2, 400.00),
(2, 2, 2, 2, 400.00),
(3, 3, 2, 1, 400.00),
(4, 2, 3, 1, 400.00),
(5, 3, 4, 1, 400.00);

-- --------------------------------------------------------

--
-- Table structure for table `payments`
--

DROP TABLE IF EXISTS `payments`;
CREATE TABLE IF NOT EXISTS `payments` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_id` bigint(20) NOT NULL,
  `amount` double(8,2) NOT NULL,
  `created_at` timestamp NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_payments_orders` (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `payments`
--

INSERT INTO `payments` (`id`, `order_id`, `amount`, `created_at`) VALUES
(1, 3, 300.00, '2018-12-16 21:51:52'),
(2, 3, 50.00, '2018-12-18 20:29:54'),
(3, 3, 10.00, '2018-12-18 21:38:49'),
(4, 3, 30.00, '2018-12-18 21:38:49'),
(5, 3, 3.00, '2018-12-18 21:38:49'),
(6, 3, 7.00, '2018-12-18 21:38:49'),
(7, 2, 200.00, '2018-12-18 21:55:21');

-- --------------------------------------------------------

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
CREATE TABLE IF NOT EXISTS `products` (
  `code` bigint(20) NOT NULL AUTO_INCREMENT,
  `category_id` bigint(20) NOT NULL,
  `label` varchar(128) NOT NULL,
  `qty` int(10) UNSIGNED NOT NULL,
  `buyingPrice` double(8,2) NOT NULL,
  `sellingPrice` double(8,2) NOT NULL,
  `image` varchar(255) NOT NULL,
  PRIMARY KEY (`code`),
  KEY `fk_products_categories` (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`code`, `category_id`, `label`, `qty`, `buyingPrice`, `sellingPrice`, `image`) VALUES
(2, 2, 'Jacket 1', 5, 300.00, 400.00, 'resources/images/products/1544019511080_5bb36112554c7f08176ec0e9.png'),
(3, 2, 'Jacket 2', 8, 300.00, 400.00, 'resources/images/products/1544022065674_5bb360f7554c7f08176ec0e5.png');

--
-- Constraints for dumped tables
--

--
-- Constraints for table `orders`
--
ALTER TABLE `orders`
  ADD CONSTRAINT `fk_orders_customers` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`);

--
-- Constraints for table `order_lines`
--
ALTER TABLE `order_lines`
  ADD CONSTRAINT `fk_order_lines_orders` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_order_lines_products` FOREIGN KEY (`product_id`) REFERENCES `products` (`code`);

--
-- Constraints for table `payments`
--
ALTER TABLE `payments`
  ADD CONSTRAINT `fk_payments_orders` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `products`
--
ALTER TABLE `products`
  ADD CONSTRAINT `fk_products_categories` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
