package com.zendesk.zendeskticketviewerbackend.service;

import com.zendesk.zendeskticketviewerbackend.model.TicketDTO;
import com.zendesk.zendeskticketviewerbackend.service.model.GetZendeskTicketResponse;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.zendesk.zendeskticketviewerbackend.service.ZendeskAPI.basicUrl;

@Service
public class ZendeskTicketService {

    private final ZendeskAPI zendeskAPI;

    public ZendeskTicketService(ZendeskAPI zendeskAPI) {
        this.zendeskAPI = zendeskAPI;
    }

    public List<TicketDTO> getAllTickets (String subdomain, String authHeader) {

        GetZendeskTicketResponse getZendeskTicketResponse = zendeskAPI.getTicketsWithCursorPagination(subdomain, authHeader, basicUrl);
        List<TicketDTO> allTickets = getZendeskTicketResponse.getTickets();

        while (getZendeskTicketResponse.getMeta().isHasMore()) {
            getZendeskTicketResponse = zendeskAPI.getTicketsWithCursorPagination(subdomain, authHeader, getZendeskTicketResponse.getLinks().getNext());
            allTickets.addAll(getZendeskTicketResponse.getTickets());
        }
        return allTickets;
    }
}
