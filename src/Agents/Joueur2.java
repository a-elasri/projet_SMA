package Agents;

import Containers.Joueur2Container;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

public class Joueur2 extends GuiAgent {

    private int compteur = 0;
    private Joueur2Container myGui;
    private Joueur2 joueur2 = this;
    static ListView list;
    public ObservableList<String> observableList = FXCollections.observableArrayList();

    public void onGuiEvent(GuiEvent guiEvent) {
        String message = guiEvent.getParameter(0).toString();
        ACLMessage messageAcl = new ACLMessage(ACLMessage.INFORM);
        messageAcl.addReceiver(new AID(guiEvent.getParameter(1).toString(), AID.ISLOCALNAME));
        list = (ListView) guiEvent.getParameter(2);
        list.setItems(observableList);
        messageAcl.setContent(message);
        send(messageAcl);
    }

    @Override
    protected void setup() {
        myGui = (Joueur2Container) getArguments()[0];
        myGui.setAgent(joueur2);
        ParallelBehaviour parallelBehaviour = new ParallelBehaviour();
        addBehaviour(parallelBehaviour);

        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage aclMessage = receive();
                if (aclMessage != null) {
                    observableList.add(aclMessage.getContent());
                } else {
                    block();
                }
            }
        });
        System.out.println("*****" + getLocalName() + " démarré *****");
    }

    @Override
    protected void takeDown() {
        System.out.println("***** Agent " + getLocalName() + " terminated *****");
    }

    @Override
    protected void beforeMove() {
        System.out.println("*** Agent " + getLocalName() + " before move ***");
    }
    @Override
    protected void afterMove() {
        System.out.println("*** Agent " + getLocalName() + " after move ***");
    }

}
