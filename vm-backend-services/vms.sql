-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 01, 2019 at 07:42 PM
-- Server version: 10.1.36-MariaDB
-- PHP Version: 7.2.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `vms`
--

-- --------------------------------------------------------

--
-- Table structure for table `all_roles`
--

CREATE TABLE `all_roles` (
  `role_code` varchar(10) NOT NULL,
  `role_description` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `employee`
--

CREATE TABLE `employee` (
  `id` int(11) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `mobile` varchar(20) DEFAULT NULL,
  `photo` int(11) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `employee_role`
--

CREATE TABLE `employee_role` (
  `emp_id` int(11) DEFAULT NULL,
  `role_code` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `images`
--

CREATE TABLE `images` (
  `id` int(11) NOT NULL,
  `image_data` longtext
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `security`
--

CREATE TABLE `security` (
  `id` int(11) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `location_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `security_locations`
--

CREATE TABLE `security_locations` (
  `id` int(11) NOT NULL,
  `description` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `security_role`
--

CREATE TABLE `security_role` (
  `security_id` int(11) DEFAULT NULL,
  `role_code` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `visitor`
--

CREATE TABLE `visitor` (
  `id` int(11) NOT NULL,
  `visitor_type_cd` varchar(10) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `mobile` varchar(20) DEFAULT NULL,
  `uploaded_photo` int(11) DEFAULT NULL,
  `actual_photo` int(11) DEFAULT NULL,
  `refered_by` int(11) DEFAULT NULL,
  `expected_in_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `actual_in_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `expected_out_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `actual_out_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `status` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `visitor_access`
--

CREATE TABLE `visitor_access` (
  `role_code` varchar(10) DEFAULT NULL,
  `visitor_type_cd` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `visitor_movement_log`
--

CREATE TABLE `visitor_movement_log` (
  `id` int(11) NOT NULL,
  `visitor_id` int(11) DEFAULT NULL,
  `location_id` int(11) DEFAULT NULL,
  `event` varchar(5) DEFAULT NULL,
  `event_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `visitor_status`
--

CREATE TABLE `visitor_status` (
  `id` int(11) NOT NULL,
  `description` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `visitor_type`
--

CREATE TABLE `visitor_type` (
  `visitor_type_cd` varchar(10) NOT NULL,
  `visitor_type_desc` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `all_roles`
--
ALTER TABLE `all_roles`
  ADD PRIMARY KEY (`role_code`);

--
-- Indexes for table `employee`
--
ALTER TABLE `employee`
  ADD PRIMARY KEY (`id`),
  ADD KEY `employee_images_photo` (`photo`);

--
-- Indexes for table `employee_role`
--
ALTER TABLE `employee_role`
  ADD KEY `employee_role_employee_emp_id` (`emp_id`),
  ADD KEY `employee_role_all_roles_role_code` (`role_code`);

--
-- Indexes for table `images`
--
ALTER TABLE `images`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `security`
--
ALTER TABLE `security`
  ADD PRIMARY KEY (`id`),
  ADD KEY `security_security_locations_location_id` (`location_id`);

--
-- Indexes for table `security_locations`
--
ALTER TABLE `security_locations`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `security_role`
--
ALTER TABLE `security_role`
  ADD KEY `security_role_security_security_id` (`security_id`),
  ADD KEY `security_role_all_roles_role_code` (`role_code`);

--
-- Indexes for table `visitor`
--
ALTER TABLE `visitor`
  ADD PRIMARY KEY (`id`),
  ADD KEY `visitor_visitor_type_visitor_type_cd` (`visitor_type_cd`),
  ADD KEY `visitor_images_uploaded_photo` (`uploaded_photo`),
  ADD KEY `visitor_images_actual_photo` (`actual_photo`),
  ADD KEY `visitor_employee_refered_by` (`refered_by`),
  ADD KEY `visitor_visitor_status_status` (`status`);

--
-- Indexes for table `visitor_access`
--
ALTER TABLE `visitor_access`
  ADD KEY `visitor_access_all_roles_role_code` (`role_code`),
  ADD KEY `visitor_access_visitor_type_visitor_type_cd` (`visitor_type_cd`);

--
-- Indexes for table `visitor_movement_log`
--
ALTER TABLE `visitor_movement_log`
  ADD PRIMARY KEY (`id`),
  ADD KEY `visitor_movement_log_visitor_visitor_id` (`visitor_id`),
  ADD KEY `visitor_movement_log_security_locations_location_id` (`location_id`);

--
-- Indexes for table `visitor_status`
--
ALTER TABLE `visitor_status`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `visitor_type`
--
ALTER TABLE `visitor_type`
  ADD PRIMARY KEY (`visitor_type_cd`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `employee`
--
ALTER TABLE `employee`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `images`
--
ALTER TABLE `images`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `security`
--
ALTER TABLE `security`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `security_locations`
--
ALTER TABLE `security_locations`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `visitor`
--
ALTER TABLE `visitor`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `visitor_movement_log`
--
ALTER TABLE `visitor_movement_log`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `visitor_status`
--
ALTER TABLE `visitor_status`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `employee`
--
ALTER TABLE `employee`
  ADD CONSTRAINT `employee_images_photo` FOREIGN KEY (`photo`) REFERENCES `images` (`id`);

--
-- Constraints for table `employee_role`
--
ALTER TABLE `employee_role`
  ADD CONSTRAINT `employee_role_all_roles_role_code` FOREIGN KEY (`role_code`) REFERENCES `all_roles` (`role_code`),
  ADD CONSTRAINT `employee_role_employee_emp_id` FOREIGN KEY (`emp_id`) REFERENCES `employee` (`id`);

--
-- Constraints for table `security`
--
ALTER TABLE `security`
  ADD CONSTRAINT `security_security_locations_location_id` FOREIGN KEY (`location_id`) REFERENCES `security_locations` (`id`);

--
-- Constraints for table `security_role`
--
ALTER TABLE `security_role`
  ADD CONSTRAINT `security_role_all_roles_role_code` FOREIGN KEY (`role_code`) REFERENCES `all_roles` (`role_code`),
  ADD CONSTRAINT `security_role_security_security_id` FOREIGN KEY (`security_id`) REFERENCES `security` (`id`);

--
-- Constraints for table `visitor`
--
ALTER TABLE `visitor`
  ADD CONSTRAINT `visitor_employee_refered_by` FOREIGN KEY (`refered_by`) REFERENCES `employee` (`id`),
  ADD CONSTRAINT `visitor_images_actual_photo` FOREIGN KEY (`actual_photo`) REFERENCES `images` (`id`),
  ADD CONSTRAINT `visitor_images_uploaded_photo` FOREIGN KEY (`uploaded_photo`) REFERENCES `images` (`id`),
  ADD CONSTRAINT `visitor_visitor_status_status` FOREIGN KEY (`status`) REFERENCES `visitor_status` (`id`),
  ADD CONSTRAINT `visitor_visitor_type_visitor_type_cd` FOREIGN KEY (`visitor_type_cd`) REFERENCES `visitor_type` (`visitor_type_cd`);

--
-- Constraints for table `visitor_access`
--
ALTER TABLE `visitor_access`
  ADD CONSTRAINT `visitor_access_all_roles_role_code` FOREIGN KEY (`role_code`) REFERENCES `all_roles` (`role_code`),
  ADD CONSTRAINT `visitor_access_visitor_type_visitor_type_cd` FOREIGN KEY (`visitor_type_cd`) REFERENCES `visitor_type` (`visitor_type_cd`);

--
-- Constraints for table `visitor_movement_log`
--
ALTER TABLE `visitor_movement_log`
  ADD CONSTRAINT `visitor_movement_log_security_locations_location_id` FOREIGN KEY (`location_id`) REFERENCES `security_locations` (`id`),
  ADD CONSTRAINT `visitor_movement_log_visitor_visitor_id` FOREIGN KEY (`visitor_id`) REFERENCES `visitor` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
