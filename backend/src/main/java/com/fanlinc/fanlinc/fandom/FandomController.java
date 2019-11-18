package com.fanlinc.fanlinc.fandom;

import com.fanlinc.fanlinc.exceptions.FandomExistsException;
import com.fanlinc.fanlinc.fandom.FandomService;
import com.fanlinc.fanlinc.user.User;
import com.fanlinc.fanlinc.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@RestController    // This means that this class is a Controller
@RequestMapping(path="/api/fandoms")
public class FandomController {
    private final FandomService fservice;
    private final UserService service;

    public FandomController(FandomService fservice, UserService service)  {
        this.fservice = fservice;
        this.service = service;
    }

    @CrossOrigin(origins = "*")
    @GetMapping(path="/findFandomById") // Map ONLY GET Requests
    @ResponseBody
    public Fandom findFandomById(@RequestParam long id) {
        return fservice.findByFandomId(id);
    }


    @CrossOrigin(origins ="*")
    @GetMapping(path="/findSimilarFandomByName") // Map ONLY GET Requests
    @ResponseBody
    public List<Fandom> findSimilarFandomByName (@RequestParam String name) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request
        return fservice.findSimilarFandomByName(name);
    }

    @CrossOrigin(origins ="*")
    @GetMapping(path="/findUser") // Map ONLY GET Requests
    @ResponseBody
    public User findUser (@RequestParam Long userId, @RequestParam Long fandomId) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request
        Fandom fandom = findFandomById(fandomId);
        Set<User> users = fandom.getUser();
        User temp = null;
        for (User user: fandom.getUser()){
            System.out.println("users:" + user.getId());
            if (user.getId().equals(userId)) {
                temp = user;
            }
        }
        return temp;
    }

    @PostMapping(path="/createFandom") // Map ONLY POST Requests
    public Fandom createNewFandom (@RequestBody Fandom fandom) throws FandomExistsException {
        // @ResponseBody means the returned String is the response, not a view name
//        System.out.println(body);
//        System.out.println("testing "+fandomName);
//        System.out.println("testing "+email);
        String email = fandom.getOwnerEmail();
        String fandomName = fandom.getFandomName();
        if (fservice.findByFandomName(fandomName) != null) {
            throw new FandomExistsException(fandomName);
        }
        User user = service.findByEmail(email);
        Long ownerId = user.getId();
        String name = user.getFirstName()+user.getLastName();
//        System.out.println("Owner Id: "+ownerId);
//        System.out.println("Owner Name: "+name);
        fandom.setUsers(user);
        user.setFandoms(fandom);
//        System.out.println("fandomId: "+fandomId);
        return fservice.save(fandom);
    }

    @CrossOrigin(origins = "*")
    @PostMapping(path="/joinFandom") // Map ONLY POST Requests
    @ResponseBody
    public void JoinFandom (@RequestBody Map<String, String> values) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request
        User user = service.findByEmail(values.get("email"));
        Fandom fandom = fservice.findByFandomName(values.get("fandomName"));
        System.out.println(fandom.getFandomId());
        System.out.println(user.getId());
        fandom.setUsers(user);
        user.setFandoms(fandom);
        fservice.save(fandom);
    }

    @CrossOrigin(origins = "*")
    @PostMapping(path="/quitFandom") // Map ONLY POST Requests
    @ResponseBody
    public void QuitFandom (@RequestBody Map<String, String> values) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request
        User user = service.findByEmail(values.get("email"));
        Fandom fandom = fservice.findByFandomName(values.get("fandomName"));
        Long fidtoremove = fandom.getFandomId();
        Long uidtoremove = user.getId();
        System.out.println("fandomId to remove: "+fidtoremove);
        System.out.println("userId to remove: "+uidtoremove);
        System.out.println(user.getFandoms().size());
        System.out.println(fandom.getUser().size());
        System.out.println(user.getFandoms());
        System.out.println(fandom.getUser());
        System.out.println(fandom);
        System.out.println(user);
        for (User users: fandom.getUser()){
            System.out.println("users:" + users.getId());
            if (users.getId().equals(uidtoremove)){
                User temp = users;
                fandom.removeUser(temp);
                System.out.println("removing user...:" + users.getId());
            }

        }
        for (Fandom fandoms: user.getFandoms()) {
            System.out.println("fans: " + fandoms.getFandomId());
            if (fandoms.getFandomId().equals(fidtoremove)) {
                Fandom temp = fandoms;
                user.removeFandom(temp);
                System.out.println("removing fandom...:" + fandom.getFandomId());
            }
        }
        System.out.println(user.getFandoms());
        System.out.println(fandom.getUser());
        System.out.println(user.getFandoms().size());
        System.out.println(fandom.getUser().size());
        fservice.save(fandom);
    }

}
