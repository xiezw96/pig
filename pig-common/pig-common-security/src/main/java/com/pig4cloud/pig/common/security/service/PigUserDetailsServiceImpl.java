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

package com.pig4cloud.pig.common.security.service;

import com.pig4cloud.pig.common.security.extension.Authenticator;
import com.pig4cloud.pig.common.security.util.ClientDetailsThreadLocal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;

/**
 * 用户详细信息
 *
 * @author lengleng
 */
@Slf4j
@Service
public class PigUserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private List<Authenticator> authenticators = Collections.emptyList();

	/**
	 * 用户密码登录
	 *
	 * @param username 用户名
	 * @return
	 * @throws UsernameNotFoundException
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		ClientDetails clientDetails = ClientDetailsThreadLocal.get();
		String userType = "";
		if (clientDetails != null) {
			userType = (String) clientDetails.getAdditionalInformation().get(ClientDetailsThreadLocal.USER_TYPE_KEY);
		}
		if (StringUtils.isEmpty(userType)) userType = "";
		return authenticators.stream()
			.filter(Authenticator::support)
			.findFirst()
			.orElseThrow(() -> new InternalAuthenticationServiceException("未知登录类型，请检查auth_type参数"))
			.authenticate(username, userType);
	}
}
