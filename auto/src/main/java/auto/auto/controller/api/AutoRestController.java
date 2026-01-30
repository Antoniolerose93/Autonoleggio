package auto.auto.controller.api;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //restituisce un json
@CrossOrigin //permette di chiamare queste api anche da un dominio diverso da quello dell'applicazione
//Il senso di esporre delle Rest alcune volte è per implementare delle applicazioni nello stesso dominio, 
// però il front-end è separato dal back-end(non è un mvc)
//Oppure posso voler controllare alcuni flussi, e non voglio permettere alcuni accessi, alcune tipologie di accessi.

@RequestMapping("/api/auto")
public class AutoRestController {

}
