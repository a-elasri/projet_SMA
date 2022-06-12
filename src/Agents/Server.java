package Agents;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.AMSService;
import jade.domain.FIPAAgentManagement.AMSAgentDescription;
import jade.domain.FIPAAgentManagement.SearchConstraints;
import jade.lang.acl.ACLMessage;

import java.util.Locale;

public class Server extends Agent {

    @Override
    protected void setup() {
        //Généré aléatoirement un nombre magique entre 0 et 100
        int nbrMagique = (int) (Math.random() * 100);
        System.out.println("Le nombre magique est: "+nbrMagique);
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage aclMessage = receive();
                if (aclMessage != null) {
                    int usernumber = 0;
                    ACLMessage messageAcl = new ACLMessage(ACLMessage.INFORM);
                    try {
                        usernumber = Integer.parseInt(aclMessage.getContent());
                        if (usernumber == nbrMagique) {
                            // Récupérer tous les agents
                            AMSAgentDescription [] agents = null;
                            SearchConstraints c = new SearchConstraints();
                            c.setMaxResults ( new Long(-1) );
                            agents = AMSService.search(Server.this, new AMSAgentDescription(), c);

                            // Envoyer un message à tous les joueurs pour l’informer du nombre magique trouvé et arrête le jeu
                            for (int i=0; i<agents.length;i++){
                                messageAcl.addReceiver(new AID(agents[i].getName().getLocalName(), AID.ISLOCALNAME));
                            }
                            messageAcl.setContent(aclMessage.getSender().getLocalName().toUpperCase(Locale.ROOT)+" Est le gagnant => Jeu terminé!!!");
                            send(messageAcl);
                            doDelete();
                        } else if (usernumber > nbrMagique) {
                            messageAcl.addReceiver(new AID(aclMessage.getSender().getLocalName(), AID.ISLOCALNAME));
                            messageAcl.setContent("Vous êtes au dessus du nombre magique");
                            send(messageAcl);
                        } else {
                            messageAcl.addReceiver(new AID(aclMessage.getSender().getLocalName(), AID.ISLOCALNAME));
                            messageAcl.setContent("Vous êtes en dessous du nombre magique");
                            send(messageAcl);
                        }
                    } catch (Exception e) {
                        messageAcl.addReceiver(new AID(aclMessage.getSender().getLocalName(), AID.ISLOCALNAME));
                        messageAcl.setContent("Saisir un numéro");
                        send(messageAcl);
                    }
                } else {
                    block();
                }

            }
        });
        System.out.println("*** Agent " + getLocalName() + " started ***");
    }

    @Override
    protected void takeDown() {
        System.out.println("*** Agent " + getLocalName() + " terminated ***");
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
