package Containers;

import jade.core.ProfileImpl;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;
import jade.core.Runtime;

public class ServerContainer {
    public static void main(String[] args) throws ControllerException {
        Runtime runtime = Runtime.instance();
        ProfileImpl profile = new ProfileImpl();
        profile.setParameter(ProfileImpl.MAIN_HOST, "localhost");
        AgentContainer serverContainer = runtime.createAgentContainer(profile);
        AgentController agentController = serverContainer.createNewAgent("Server", "Agents.Server", new Object[]{});
        agentController.start();
    }
}
