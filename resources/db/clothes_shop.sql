-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Nov 29, 2018 at 08:14 PM
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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `categories`
--

INSERT INTO `categories` (`id`, `title`) VALUES
(1, 'Televisions'),
(5, 'Laptops'),
(6, 'test');

-- --------------------------------------------------------

--
-- Table structure for table `customers`
--

DROP TABLE IF EXISTS `customers`;
CREATE TABLE IF NOT EXISTS `customers` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) CHARACTER SET utf8mb4 NOT NULL,
  `firstName` varchar(255) CHARACTER SET utf8mb4 NOT NULL,
  `lastName` varchar(255) CHARACTER SET utf8mb4 NOT NULL,
  `phone` varchar(255) CHARACTER SET utf8mb4 NOT NULL,
  `address` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `customers`
--

INSERT INTO `customers` (`id`, `code`, `firstName`, `lastName`, `phone`, `address`, `email`) VALUES
(11, 'JF53974', 'Ahmed', 'NFAIHI', '0643573987', 'TanTan', 'anfaihi10@gmail.com'),
(12, 'E453295', 'Mohamed', 'BOUKHLIF', '0649056631', 'Marrakech', 'mohamed.boukhlif@gmail.com'),
(13, 'JF53998', 'Youness', 'IBENCHEROUTEN', '0655648099', 'TanTan', 'y.bench@gmail.com'),
(14, 'N117951', 'Afraa', 'FAIK', '0645631105', 'Essaouira', 'afraa.faik@gmail.com');

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
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `orders`
--

INSERT INTO `orders` (`id`, `customer_id`, `total`, `created_at`) VALUES
(6, 14, 31610.00, '2018-11-25 00:00:00'),
(7, 13, 499.00, '2018-11-26 00:00:00'),
(8, 14, 66400.00, '2018-11-26 00:00:00'),
(9, 14, 8455.00, '2018-11-26 00:00:00'),
(10, 11, 2022.00, '2018-11-26 00:00:00'),
(11, 11, 400.00, '2018-11-26 00:00:00'),
(12, 12, 1000.00, '2018-11-26 10:53:20'),
(13, 11, 2775.00, '2018-11-28 00:13:02'),
(14, 11, 2775.00, '2018-11-28 00:15:37');

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
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `order_lines`
--

INSERT INTO `order_lines` (`id`, `product_id`, `order_id`, `qty`, `price`) VALUES
(5, 5, 6, 5, 5656.00),
(6, 6, 6, 6, 555.00),
(7, 1, 7, 1, 400.00),
(8, 2, 7, 9, 11.00),
(9, 1, 8, 166, 400.00),
(10, 1, 9, 21, 400.00),
(11, 2, 9, 5, 11.00),
(12, 1, 10, 5, 400.00),
(13, 2, 10, 2, 11.00),
(14, 1, 11, 1, 400.00),
(15, 4, 12, 1, 1000.00),
(16, 11, 13, 5, 555.00),
(17, 11, 14, 5, 555.00);

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
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`code`, `category_id`, `label`, `qty`, `buyingPrice`, `sellingPrice`, `image`) VALUES
(1, 1, 'TV', 2, 200.00, 400.00, 'admin.jpg'),
(2, 1, 'azerty', 0, 10.00, 11.00, 'resources//images//products//1542759185347.png'),
(3, 1, 'test', 0, 200.00, 400.00, 'resources//images//products//1542759382309_icons8-add-image-64.png.png'),
(4, 1, 'teee', 0, 500.00, 1000.00, 'resources//images//products//1542759532449_weBTS.png.png'),
(5, 1, 'test', 0, 56.00, 5656.00, 'resources//images//products//1542759610860_weBTS.png'),
(6, 1, 'tttt', 0, 50.00, 555.00, 'resources//images//products//1542760934437_Capture.JPG'),
(7, 1, '56', 0, 56.00, 56.00, 'resources//images//products//1542761435229_weBTS.png'),
(8, 1, '4554', 0, 4545.00, 445.00, 'resources/images/products/1542761550413_weBTS.png'),
(9, 1, '56', 0, 56.00, 56.00, 'resources/images/products/1542761733267_Capture.JPG'),
(10, 5, 'Produit1', 0, 500.00, 1000.00, 'C:\\eclipse-workspace\\TPsMrNaji\\clothes_shop\\resources\\images\\products\\1543109143270_44917868_557893624664857_4929024805283299328_n.jpg'),
(11, 1, 'test', 2, 55.00, 555.00, 'C:\\eclipse-workspace\\TPsMrNaji\\clothes_shop\\resources\\images\\products\\1543355301543_44917868_557893624664857_4929024805283299328_n.jpg');

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
-- Constraints for table `products`
--
ALTER TABLE `products`
  ADD CONSTRAINT `fk_products_categories` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
