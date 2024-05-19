package com.finki.wp.workoutapp.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.finki.wp.workoutapp.model.TrainingDay;
import com.finki.wp.workoutapp.model.User;
import com.finki.wp.workoutapp.model.Workouts;
import com.finki.wp.workoutapp.service.ITrainingDayService;
import com.finki.wp.workoutapp.service.IUserService;
import com.finki.wp.workoutapp.service.IWorkoutsService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class EventController {

    private final IUserService userService;
    private final ITrainingDayService trainingDayService;

    private final IWorkoutsService workoutsService;

    public EventController(IUserService userService, ITrainingDayService trainingDayService, IWorkoutsService workoutsService) {
        this.userService = userService;
        this.trainingDayService = trainingDayService;
        this.workoutsService = workoutsService;
    }
    public static String convertEventsToJson(List<TrainingDay> events) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            ArrayNode jsonArray = objectMapper.createArrayNode();

            for (TrainingDay event : events) {
                for (Workouts wo : event.getWorkouts()){
                    ObjectNode eventJson = objectMapper.createObjectNode();
                    eventJson.put("title", wo.getWorkoutName());
                    eventJson.put("start", event.getDate().toString());
                    jsonArray.add(eventJson);
                }
            }

            return jsonArray.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @GetMapping("/events")
    @ResponseBody
    public String getEvents() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findUserByUsername(userDetails.getUsername());
        List<TrainingDay> events = trainingDayService.findAllByUser(user);
        String jsonEvents = convertEventsToJson(events);
        System.out.println(jsonEvents);
        return jsonEvents;
    }

    @PostMapping("/saveEvent")
    public void saveEvent(@AuthenticationPrincipal UserDetails userDetails,
                          @RequestBody Map<String, String> payload,
                          HttpServletResponse response) throws IOException {
        String date = payload.get("date");
        String event = payload.get("event");
        User user = userService.findUserByUsername(userDetails.getUsername());
        Workouts workout = workoutsService.findWorkoutById(Long.valueOf(event)).get();
        TrainingDay trainingDay = trainingDayService.findTrainingDayByDateAndUser(Date.valueOf(date), user);

        if (trainingDay != null){
            trainingDay.getWorkouts().add(workout);
            trainingDayService.save(trainingDay);
        }
        else {
            List<Workouts> newWorkouts = new ArrayList<>();
            newWorkouts.add(workout);
            TrainingDay newTrainingDay = new TrainingDay();
            user.getTrainingDays().add(newTrainingDay);
            workout.getTrainingDays().add(newTrainingDay);

            newTrainingDay.setUser(user);
            newTrainingDay.setDate(Date.valueOf(date));
            newTrainingDay.setWorkouts(newWorkouts);
            trainingDayService.save(newTrainingDay);
        }

        response.setContentType("text/plain");
        response.getWriter().write("Event saved successfully!");
    }
}
