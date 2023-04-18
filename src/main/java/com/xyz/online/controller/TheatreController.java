package com.xyz.online.controller;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;

import com.xyz.online.entities.Screen;
import com.xyz.online.entities.Show;
import com.xyz.online.entities.Theatre;
import com.xyz.online.services.ScreenService;
import com.xyz.online.services.TheatreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/theatreOwner")
public class TheatreController {
	
	@Autowired
    TheatreService theatreService;
	
	@Autowired
    ScreenService screenService;
	
	@GetMapping("/{theatre}/{city}/{pin}/shows")
	public ResponseEntity<List<Show>> getShowsInTheatre(@PathVariable(name = "theatre") String theatreName,
                                                        @PathVariable(name = "city") String cityName, @PathVariable(name = "pin") String pinCode) {
		Theatre theatre = theatreService.getTheatreEntityByNameAndLocation(theatreName, cityName, pinCode);
		List<Show> shows = theatre.getScreens().stream()
		.flatMap(sc -> sc.getShows().stream())
		.collect(toList());
		ResponseEntity<List<Show>> re = new ResponseEntity<>(shows, HttpStatus.FOUND);
		return re;
	}
	
	@GetMapping("/{theatre}/{city}/{pin}/screens")
	public ResponseEntity<List<Screen>> getScreensInTheatre(@PathVariable(name = "theatre") String theatreName,
                                                            @PathVariable(name = "city") String cityName, @PathVariable(name = "pin") String pinCode) {
		Theatre theatre = theatreService.getTheatreEntityByNameAndLocation(theatreName, cityName, pinCode);
		List<Screen> screens = new ArrayList<>(theatre.getScreens());
		ResponseEntity<List<Screen>> re = new ResponseEntity<>(screens, HttpStatus.FOUND);
		return re;
	}
	
	@PostMapping("/{theatre}/{city}/{pin}/newShow")
	public ResponseEntity<String> createShow(@PathVariable(name = "theatre") String theatreName,
			@PathVariable(name = "city") String cityName, @PathVariable(name = "pin") String pinCode,
			@RequestBody Show show) {
		boolean addShowToScreen = theatreService.addShowToScreen(theatreName, cityName, pinCode, show);
		ResponseEntity<String> re;
		if(addShowToScreen)
			re = new ResponseEntity<>("Added the show successfully...", HttpStatus.ACCEPTED);
		else
			re = new ResponseEntity<>("Addition of show failed due to either show details are not correct or time not available for the show, \n"
					+ "Please check show details again...", HttpStatus.BAD_REQUEST);
		return re;
	}
	
	@PutMapping("/{theatre}/{city}/{pin}/updateShow")
	public ResponseEntity<Show> updateShow(@PathVariable(name = "theatre") String theatreName,
			@PathVariable(name = "city") String cityName, @PathVariable(name = "pin") String pinCode,
			@RequestBody Show show) {
		Show updatedShow = theatreService.updateShowInTheatre(theatreName, cityName, pinCode, show);
		ResponseEntity<Show> re;
		if(updatedShow != null)
			re = new ResponseEntity<>(updatedShow, HttpStatus.ACCEPTED);
		else
			re = new ResponseEntity<>(new Show(), HttpStatus.NOT_MODIFIED);
		return re;
	}
	
	@DeleteMapping("/{theatre}/{city}/{pin}/deleteShow/{showID}")
	public ResponseEntity<Show> deleteShow(@PathVariable(name = "theatre") String theatreName,
			@PathVariable(name = "city") String cityName, @PathVariable(name = "pin") String pinCode,
			@PathVariable String showID) {

		List<Screen> screens = theatreService.getTheatreEntityByNameAndLocation(theatreName, cityName, pinCode).getScreens();
		Show show = null;
		for(Screen screen : screens) {
			List<Show> shows = screen.getShows();
			show = shows.stream().filter(sh -> sh.getShowID().equals(showID))
					.findFirst().orElse(null);
			if(show != null) {
				shows.remove(show);
				break;
			}
		}
		ResponseEntity<Show> re = null;
		if(show == null)
			re = new ResponseEntity<>(new Show(), HttpStatus.NOT_FOUND);
		else
			re = new ResponseEntity<>(show, HttpStatus.OK);
		return re;
	}
}
