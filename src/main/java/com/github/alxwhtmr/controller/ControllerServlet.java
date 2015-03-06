package com.github.alxwhtmr.controller;

import com.github.alxwhtmr.entity.Muscle;
import com.github.alxwhtmr.entity.MuscleGroup;
import com.github.alxwhtmr.session.MuscleFacade;
import com.github.alxwhtmr.session.MuscleGroupFacade;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The {@code ControllerServlet} is main 
 * controller that manages all web queries
 * from clients (such as GET and POST)
 */
@WebServlet(name = "ControllerServlet",
        loadOnStartup = 1,
        urlPatterns = {
            "/showItems",
            "/new",
            "/save",
            "/delete",
            "/search",
            "/edit",
            "/change"
        })
public class ControllerServlet extends HttpServlet {

    @EJB
    private MuscleGroupFacade muscleGroupFacade;

    @EJB
    private MuscleFacade muscleFacade;

    private LinkedList<MuscleGroup> muscleGroup;

    /**
     * Initializes servlet once after it created
     * Fills the array of muscle groups - it only needs
     * to be loaded once from database
     * @throws ServletException in case of exception
     */
    @Override
    public void init() throws ServletException {
        muscleGroup = new LinkedList<MuscleGroup>();
        muscleGroup.addAll(muscleGroupFacade.findAll());
        getServletContext().setAttribute("muscleGroups", muscleGroupFacade.findAll());
    }

    /**
     * Sets the appropriate code page (UTF-8) in case of cyrillic input
     *
     * @param request of type {@code HttpServletRequest}
     * @param response of type {@code HttpServletResponse}
     * @throws ServletException in case of servlet exception
     * @throws IOException in case of I/O exception
     */
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        super.service(request, response);
    }

    /**
     * Handles the HTTP {@code GET} method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userPath = request.getServletPath();
        String url = null;

        switch (userPath) {
            case "/showItems":
                String muscleGroupId = request.getQueryString();
                if (muscleGroupId != null && muscleGroupId.equals("all")) {
                    log("show all");
                    Collection<Muscle> muscles = muscleFacade.findAll();
                    request.setAttribute("muscles", muscles);
                } else if (muscleGroupId != null) {
                    // get selected muscle group
                    MuscleGroup selectedMuscleGroup = muscleGroupFacade.find(Integer.parseInt(muscleGroupId));

                    // place selected muscle group in request scope
                    request.setAttribute("selectedMuscleGroup", selectedMuscleGroup);

                    // get all muscles for selected muscle group
                    Collection<Muscle> muscles = null;
                    LinkedList<Muscle> m2 = new LinkedList<Muscle>();
                    for (MuscleGroup mg : muscleGroup) {
                        if (mg.equals(selectedMuscleGroup)) {
                            muscles = mg.getMuscleCollection();
                        }
                    }

                    // place group muscles in request scope
                    request.setAttribute("muscles", muscles);
                }
                url = "/WEB-INF/view" + userPath + ".jsp";
                break;
            case "/search":
                log("search " + request.getQueryString());
                String searchString = request.getParameter("searchString");
                if (searchString == "") {
                    url = "/WEB-INF/view/showItems.jsp";
                    break;
                }
                Collection<Muscle> muscles = muscleFacade.findByPattern(searchString);
                if (muscles != null) {
                    for (Muscle m : muscles) {
                        request.setAttribute("muscles", muscles);
                    }
                }
                url = "/WEB-INF/view/showItems.jsp";
                break;
            case "/new":
            {
                // just return new.jsp form for input
                url = "/WEB-INF/view/" + userPath + ".jsp";
                break;
            }
            case "/edit": {
                try {
                    String muscleId = request.getQueryString();
                    log("muscleId=" + muscleId);
                    Muscle muscle = muscleFacade.findById(Integer.parseInt(muscleId));
                    request.setAttribute("muscle", muscle);
                } 
                catch (Exception e) {
                    log(e.toString());
                }
                url = "/WEB-INF/view/" + userPath + ".jsp";
                break;
            }
        }

        try {
            request.getRequestDispatcher(url).forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    /**
     * Handles the HTTP {@code POST} method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        log("doPost");
        // Set response content type
        response.setContentType("text/html");

        String userPath = request.getServletPath();

        switch (userPath) {
            case "/save": {
                try {
                    MuscleGroup selectedMuscleGroup = muscleGroupFacade.findByName(request.getParameter("muscleGroup"));
                    Muscle muscle = muscleFacade.insertNewRecord(request, selectedMuscleGroup);
                    for (MuscleGroup mg : muscleGroup) {
                        if (mg.equals(selectedMuscleGroup)) {
                            mg.getMuscleCollection().add(muscle);
                        }
                    }
                } catch (Exception e) {
                    log(e.toString());
                }
                break;
            }
            case "/delete": {
                Integer muscleId = Integer.parseInt(request.getParameter("id"));
                String groupId = request.getParameter("group");
                Muscle deletedMuscle = null;
                try {
                    Muscle muscle = muscleFacade.findById(muscleId);
                    deletedMuscle = muscleFacade.deleteRecord(muscle);
                    log("Deleted: " + deletedMuscle.getNameRu());
                    MuscleGroup editableMuscleGroup = muscleGroupFacade.find(Integer.parseInt(groupId));
                    for (MuscleGroup globalMuscleGroup : muscleGroup) {
                        if (globalMuscleGroup.equals(editableMuscleGroup)) {
                            globalMuscleGroup.getMuscleCollection().remove(deletedMuscle);
                        }
                    }
                } catch (Exception e) {
                    log(e.toString());
                }
                break;
            }
            case "/change": {
                try {
                    Muscle currentData = muscleFacade.findById(Integer.parseInt(request.getParameter("idMuscle")));
                    Muscle dataToMerge = currentData;

                    muscleFacade.editRecord(request, dataToMerge);
                    MuscleGroup editableMuscleGroup = muscleGroupFacade.find(currentData.getFkMuscleGroup().getIdMuscleGroup());

                    // loop through categories and change one record   
                    mainLoop:
                    for (int i = 0; i < muscleGroup.size(); i++) {
                        // if category = changed_record's category
                        if (muscleGroup.get(i).equals(editableMuscleGroup)) {
                            int size = muscleGroup.get(i).getMuscleCollection().size();
                            // loop through every muscle in category
                            for (int j = 0; j < size; j++) {
                                LinkedList<Muscle> muscleList = new LinkedList<Muscle>();
                                muscleList.addAll(muscleGroup.get(i).getMuscleCollection());
                                
                                // the muscle that needs to be changed is 'currentData', so find it and merge changes
                                if (muscleList.get(j).equals(currentData)) {
                                    muscleList.remove(j);
                                    muscleList.add(currentData);
                                    muscleGroup.get(i).getMuscleCollection().clear();
                                    muscleGroup.get(i).setMuscleCollection(muscleList);
                                    break mainLoop;
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    log(e.toString());
                }
                break;
            }
        }
        try {
            log("return from post");
            String url = "/WEB-INF/view/showItems.jsp";
            request.getRequestDispatcher(url).forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
