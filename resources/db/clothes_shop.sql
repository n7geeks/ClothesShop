-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Nov 25, 2018 at 08:07 PM
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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `categories`
--

INSERT INTO `categories` (`id`, `title`) VALUES
(1, 'Televisions'),
(5, 'Laptops');

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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `orders`
--

INSERT INTO `orders` (`id`, `customer_id`, `total`, `created_at`) VALUES
(2, 1, 5000.00, '2018-11-25 00:00:31');

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
CREATE TABLE IF NOT EXISTS `products` (
  `code` bigint(20) NOT NULL AUTO_INCREMENT,
  `category_id` bigint(20) NOT NULL,
  `label` varchar(128) NOT NULL,
  `buyingPrice` double(8,2) NOT NULL,
  `sellingPrice` double(8,2) NOT NULL,
  `image` varchar(255) NOT NULL,
  PRIMARY KEY (`code`),
  KEY `fk_products_categories` (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`code`, `category_id`, `label`, `buyingPrice`, `sellingPrice`, `image`) VALUES
(1, 1, 'TV', 200.00, 400.00, 'admin.jpg'),
(2, 1, 'azerty', 10.00, 11.00, 'resources//images//products//1542759185347.png'),
(3, 1, 'test', 200.00, 400.00, 'resources//images//products//1542759382309_icons8-add-image-64.png.png'),
(4, 1, 'teee', 500.00, 1000.00, 'resources//images//products//1542759532449_weBTS.png.png'),
(5, 1, 'test', 56.00, 5656.00, 'resources//images//products//1542759610860_weBTS.png'),
(6, 1, 'tttt', 50.00, 555.00, 'resources//images//products//1542760934437_Capture.JPG'),
(7, 1, '56', 56.00, 56.00, 'resources//images//products//1542761435229_weBTS.png'),
(8, 1, '4554', 4545.00, 445.00, 'resources/images/products/1542761550413_weBTS.png'),
(9, 1, '56', 56.00, 56.00, 'resources/images/products/1542761733267_Capture.JPG'),
(10, 5, 'Produit1', 500.00, 1000.00, 'C:\\eclipse-workspace\\TPsMrNaji\\clothes_shop\\resources\\images\\products\\1543109143270_44917868_557893624664857_4929024805283299328_n.jpg');

--
-- Constraints for dumped tables
--

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
