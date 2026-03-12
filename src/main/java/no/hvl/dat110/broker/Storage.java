package no.hvl.dat110.broker;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

import no.hvl.dat110.common.TODO;
import no.hvl.dat110.common.Logger;
import no.hvl.dat110.messages.ConnectMsg;
import no.hvl.dat110.messages.CreateTopicMsg;
import no.hvl.dat110.messagetransport.Connection;

public class Storage {

	// data structure for managing subscriptions
	// maps from a topic to set of subscribed users
	protected ConcurrentHashMap<String, Set<String>> subscriptions;
	
	// data structure for managing currently connected clients
	// maps from user to corresponding client session object
	
	protected ConcurrentHashMap<String, ClientSession> clients;

	public Storage() {
		subscriptions = new ConcurrentHashMap<String, Set<String>>();
		clients = new ConcurrentHashMap<String, ClientSession>();
	}

	public Collection<ClientSession> getSessions() {
		return clients.values();
	}

	public Set<String> getTopics() {

		return subscriptions.keySet();

	}

	// get the session object for a given user
	// session object can be used to send a message to the user
	
	public ClientSession getSession(String user) {

		ClientSession session = clients.get(user);

		return session;
	}

	public Set<String> getSubscribers(String topic) {

		return (subscriptions.get(topic));

	}

	public void addClientSession(String user, Connection connection) {

		// TODO: add corresponding client session to the storage
		// See ClientSession class
		ClientSession clientSession = new ClientSession(user, connection);
        clients.put(user, clientSession);
		
	}

	public void removeClientSession(String user) {

		// TODO: disconnet the client (user) 
		// and remove client session for user from the storage
		ClientSession session = getSession(user);
        if(session!=null) {
            session.disconnect();
        }
        clients.remove(user); //koffor ikkje remove session?
		
	}

	public void createTopic(String topic) {

        Set<String> emptySubscriberList = ConcurrentHashMap.newKeySet();
        subscriptions.put(topic, emptySubscriberList);
	
	}

	public void deleteTopic(String topic) {

        subscriptions.remove(topic);
	}

	public void addSubscriber(String user, String topic) {

		// TODO: add the user as subscriber to the
        Set<String> subscribers = getSubscribers(topic);
        if(subscribers!=null){
            subscribers.add(user);
        }
        subscriptions.put(topic, subscribers); //må ikke denne linjen være her?
		
	}

	public void removeSubscriber(String user, String topic) {

		// TODO: remove the user as subscriber to the topic
        Set<String> subscribers =getSubscribers(topic);
        if(subscribers!=null){
            subscribers.remove(user);
        }
	}
}
