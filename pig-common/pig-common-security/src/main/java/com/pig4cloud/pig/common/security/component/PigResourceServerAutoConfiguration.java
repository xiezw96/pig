/*
 *  Copyright (c) 2019-2020, 冷冷 (wangiegie@gmail.com).
 *  <p>
 *  Licensed under the GNU Lesser General Public License 3.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  <p>
 * https://www.gnu.org/licenses/lgpl.html
 *  <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pig4cloud.pig.common.security.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedUserException;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

/**
 * @author lengleng
 * @date 2019/03/08
 */
@ComponentScan("com.pig4cloud.pig.common.security")
@Slf4j
public class PigResourceServerAutoConfiguration {
	@Bean
	@Primary
	@LoadBalanced
	public RestTemplate lbRestTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
			@Override
			public void handleError(ClientHttpResponse response) throws IOException {
				String responseStr = new String(getResponseBody(response), getCharset(response));
				log.error("验证token异常: RawStatusCode - {}, StatusText - {}, Headers - {}, Response - {}, Charset - {}", response.getRawStatusCode(), response.getStatusText(), response.getHeaders(), responseStr, getCharset(response));
				throw new UnauthorizedUserException("认证失败");
			}
		});
		return restTemplate;
	}
}
