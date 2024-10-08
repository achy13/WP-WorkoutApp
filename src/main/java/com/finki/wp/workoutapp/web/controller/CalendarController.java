package com.finki.wp.workoutapp.web.controller;

import com.finki.wp.workoutapp.model.Measurement;
import com.finki.wp.workoutapp.model.TrainingDay;
import com.finki.wp.workoutapp.model.User;
import com.finki.wp.workoutapp.model.Workouts;
import com.finki.wp.workoutapp.model.enums.MeasurementType;
import com.finki.wp.workoutapp.service.ITrainingDayService;
import com.finki.wp.workoutapp.service.IUserService;
import com.finki.wp.workoutapp.service.IWorkoutService;
import com.finki.wp.workoutapp.service.MeasurementService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/calendar")
public class CalendarController {

    private final IWorkoutService workoutsService;
    private final IUserService userService;
    private final ITrainingDayService trainingDayService;
    private final MeasurementService measurementService;


    public CalendarController(IWorkoutService workoutsService, IUserService userService, ITrainingDayService trainingDayService, MeasurementService measurementService) {
        this.workoutsService = workoutsService;
        this.userService = userService;
        this.trainingDayService = trainingDayService;
        this.measurementService = measurementService;
    }

    @GetMapping
    public String getCalendarPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userService.findUserByUsername(userDetails.getUsername());
        List<Workouts> workouts = workoutsService.findAllWorkoutsByUser(user);
        List<TrainingDay> events = trainingDayService.findAllByUser(user);
        Optional<Measurement> optionalMeasurement = measurementService.findMeasurementByUserAndType(user, MeasurementType.MEASUREMENT);
        if (optionalMeasurement.isPresent()) {
            Measurement measurement = optionalMeasurement.get();
            model.addAttribute("measurement", measurement);
        }

        model.addAttribute("bodyContent", "calendar");
        model.addAttribute("workouts", workouts);
        model.addAttribute("events", events);
        model.addAttribute("hasEvent", trainingDayService.hasEvent(userDetails));

        return "index";
    }

    //  "[
    //      {
    //          \"title\": \"Почеток на проект\",
    //          \"start\": \"2024-05-01\"
    //      },
    //      {\"title\": \"Средба со клиент\", \"start\": \"2024-05-05\"}, {\"title\": \"Презентација\", \"start\": \"2024-05-15\"}]";



    /*
    @GetMapping("/calendar/events")
    @ResponseBody
    public List<TrainingDay> fetchEventsFromDatabase() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findUserByUsername(userDetails.getUsername());
        List<TrainingDay> tds = trainingDayService.findAllByUser(user);
        return tds;
    }

    @PostMapping("/calendar/save-event")
    public void saveEventToDatabase(@RequestParam("title") String title, @RequestParam("date") String date) throws ParseException {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findUserByUsername(userDetails.getUsername());

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        TrainingDay training = trainingDayService.findTrainingDayByDateAndUser(sdf.parse(date), user);
        Workouts workout = workoutsService.findByWorkoutName(title);

        if (training != null){
            training.getWorkouts().add(workout);
        }
        else {
            List<Workouts> workouts = new ArrayList<>();
            workouts.add(workout);
            TrainingDay newTraining = new TrainingDay(user, sdf.parse(date), workouts);
            user.getTrainingDays().add(newTraining);
        }

    }

     */
}
