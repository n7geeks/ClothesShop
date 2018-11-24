-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Nov 24, 2018 at 11:59 PM
-- Server version: 10.1.16-MariaDB
-- PHP Version: 5.6.24

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
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

CREATE TABLE `categories` (
  `id` bigint(20) NOT NULL,
  `title` varchar(128) CHARACTER SET latin1 DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `categories`
--

INSERT INTO `categories` (`id`, `title`) VALUES
(1, 'Televisions'),
(5, 'Laptops');

-- --------------------------------------------------------

--
-- Table structure for table `products`
--

CREATE TABLE `products` (
  `code` bigint(20) NOT NULL,
  `label` varchar(128) NOT NULL,
  `buyingPrice` double(8,2) NOT NULL,
  `sellingPrice` double(8,2) NOT NULL,
  `image` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`code`, `label`, `buyingPrice`, `sellingPrice`, `image`) VALUES
(1, 'TV', 200.00, 400.00, 'admin.jpg'),
(2, 'azerty', 10.00, 11.00, 'resources//images//products//1542759185347.png'),
(3, 'test', 200.00, 400.00, 'resources//images//products//1542759382309_icons8-add-image-64.png.png'),
(4, 'teee', 500.00, 1000.00, 'resources//images//products//1542759532449_weBTS.png.png'),
(5, 'test', 56.00, 5656.00, 'resources//images//products//1542759610860_weBTS.png'),
(6, 'tttt', 50.00, 555.00, 'resources//images//products//1542760934437_Capture.JPG'),
(7, '56', 56.00, 56.00, 'resources//images//products//1542761435229_weBTS.png'),
(8, '4554', 4545.00, 445.00, 'resources/images/products/1542761550413_weBTS.png'),
(9, '56', 56.00, 56.00, 'resources/images/products/1542761733267_Capture.JPG');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `categories`
--
ALTER TABLE `categories`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`code`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `categories`
--
ALTER TABLE `categories`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT for table `products`
--
ALTER TABLE `products`
  MODIFY `code` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
