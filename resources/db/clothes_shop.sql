-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Client :  127.0.0.1
-- Généré le :  Dim 25 Novembre 2018 à 22:20
-- Version du serveur :  10.1.13-MariaDB
-- Version de PHP :  7.0.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `clothes_shop`
--

-- --------------------------------------------------------

--
-- Structure de la table `categories`
--

CREATE TABLE `categories` (
  `id` bigint(20) NOT NULL,
  `title` varchar(128) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Contenu de la table `categories`
--

INSERT INTO `categories` (`id`, `title`) VALUES
(1, 'Televisions'),
(5, 'Laptops');

-- --------------------------------------------------------

--
-- Structure de la table `customers`
--

CREATE TABLE `customers` (
  `id` int(11) NOT NULL,
  `code` varchar(255) NOT NULL,
  `firstName` varchar(255) NOT NULL,
  `lastName` varchar(255) NOT NULL,
  `phone` varchar(255) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `customers`
--

INSERT INTO `customers` (`id`, `code`, `firstName`, `lastName`, `phone`, `address`, `email`) VALUES
(11, 'JF53974', 'Ahmed', 'NFAIHI', '0643573987', 'TanTan', 'anfaihi10@gmail.com'),
(12, 'E453295', 'Mohamed', 'BOUKHLIF', '0649056631', 'Marrakech', 'mohamed.boukhlif@gmail.com'),
(13, 'JF53998', 'Youness', 'IBENCHEROUTEN', '0655648099', 'TanTan', 'y.bench@gmail.com'),
(14, 'N117953', 'Afraa', 'FAIK', '0645631105', 'Essaouira', 'afraa.faik@gmail.com');

-- --------------------------------------------------------

--
-- Structure de la table `orders`
--

CREATE TABLE `orders` (
  `id` bigint(20) NOT NULL,
  `customer_id` bigint(20) NOT NULL,
  `total` double(8,2) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Contenu de la table `orders`
--

INSERT INTO `orders` (`id`, `customer_id`, `total`, `created_at`) VALUES
(2, 1, 5000.00, '2018-11-25 00:00:31');

-- --------------------------------------------------------

--
-- Structure de la table `order_lines`
--

CREATE TABLE `order_lines` (
  `id` bigint(20) NOT NULL,
  `product_id` bigint(20) NOT NULL,
  `order_id` bigint(20) NOT NULL,
  `qty` int(11) NOT NULL,
  `price` double(8,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Structure de la table `products`
--

CREATE TABLE `products` (
  `code` bigint(20) NOT NULL,
  `category_id` bigint(20) NOT NULL,
  `label` varchar(128) NOT NULL,
  `buyingPrice` double(8,2) NOT NULL,
  `sellingPrice` double(8,2) NOT NULL,
  `image` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Contenu de la table `products`
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
-- Index pour les tables exportées
--

--
-- Index pour la table `categories`
--
ALTER TABLE `categories`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `customers`
--
ALTER TABLE `customers`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `order_lines`
--
ALTER TABLE `order_lines`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_order_lines_products` (`product_id`),
  ADD KEY `fk_order_lines_orders` (`order_id`);

--
-- Index pour la table `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`code`),
  ADD KEY `fk_products_categories` (`category_id`);

--
-- AUTO_INCREMENT pour les tables exportées
--

--
-- AUTO_INCREMENT pour la table `categories`
--
ALTER TABLE `categories`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT pour la table `customers`
--
ALTER TABLE `customers`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;
--
-- AUTO_INCREMENT pour la table `orders`
--
ALTER TABLE `orders`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT pour la table `order_lines`
--
ALTER TABLE `order_lines`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT pour la table `products`
--
ALTER TABLE `products`
  MODIFY `code` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
--
-- Contraintes pour les tables exportées
--

--
-- Contraintes pour la table `order_lines`
--
ALTER TABLE `order_lines`
  ADD CONSTRAINT `fk_order_lines_orders` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_order_lines_products` FOREIGN KEY (`product_id`) REFERENCES `products` (`code`);

--
-- Contraintes pour la table `products`
--
ALTER TABLE `products`
  ADD CONSTRAINT `fk_products_categories` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
