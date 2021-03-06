package jp.ac.nii.prl.mape.monitoring.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.amazonaws.services.ec2.model.IpPermission;

import jp.ac.nii.prl.mape.monitoring.model.FirewallRule;

@Service("firewallRuleService")
public class FirewallRuleServiceImpl implements FirewallRuleService {
	
	/* (non-Javadoc)
	 * @see jp.ac.nii.prl.mape.monitoring.service.FirewallRuleService#fromAWS(com.amazonaws.services.ec2.model.IpPermission)
	 */
	@Override
	public FirewallRule fromAWS(IpPermission ipPermission) {
		FirewallRule firewallRule = new FirewallRule();
		
		firewallRule.setFwRuleID(UUID.randomUUID().toString());
		firewallRule.setFwStatus(0);
		if (!ipPermission.getIpRanges().isEmpty()) // IP-based rule
			firewallRule.setIp(ipPermission.getIpRanges().get(0));
		else // SG-based rule
			firewallRule.setIp(ipPermission.getUserIdGroupPairs().iterator().next().getGroupId());
		firewallRule.setOutbound(false);
		if (ipPermission.getToPort() != null)
			firewallRule.setPort(ipPermission.getToPort().toString());
		firewallRule.setProtocol(ipPermission.getIpProtocol());
		
		return firewallRule;
	}
}
