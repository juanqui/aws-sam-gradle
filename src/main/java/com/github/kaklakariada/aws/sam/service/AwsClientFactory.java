/**
 * aws-sam-gradle - Gradle plugin for deploying AWS Serverless Application Models
 * Copyright (C) 2017 Christoph Pirkl <christoph at users.sourceforge.net>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.github.kaklakariada.aws.sam.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;

public class AwsClientFactory {

	private static final Logger LOG = LoggerFactory.getLogger(AwsClientFactory.class);

	private final Regions region;
	private final AWSCredentialsProvider credentialsProvider;

	private AwsClientFactory(Regions region, AWSCredentialsProvider credentialsProvider) {
		this.region = region;
		this.credentialsProvider = credentialsProvider;
	}

	public static AwsClientFactory create(Regions region, String profileName) {
		LOG.info("Using region {} and profile {}", region, profileName);
		final AWSCredentialsProvider credentialsProvider = new ProfileCredentialsProvider(profileName);
		return new AwsClientFactory(region, credentialsProvider);
	}

	public <T, B extends AwsClientBuilder<B, T>> T create(final AwsClientBuilder<B, T> builder) {
		return builder //
				.withCredentials(credentialsProvider) //
				.withRegion(region) //
				.build();
	}
}
