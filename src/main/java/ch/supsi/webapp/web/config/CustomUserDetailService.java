package ch.supsi.webapp.web.config;

import ch.supsi.webapp.web.model.User;
import ch.supsi.webapp.web.service.TicketService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailService implements UserDetailsService {

	private TicketService ticketService;

	public CustomUserDetailService(TicketService ticketService){
		this.ticketService = ticketService;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = ticketService.findUserByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found");
		}
		List<GrantedAuthority> auth = AuthorityUtils.createAuthorityList (user.getRole().getName());
		return new org.springframework.security.core.userdetails.User(username, user.getPassword(), true, true, true, true, auth);
	}

}
