package cl.volvo.productoservice.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

import cl.volvo.productoservice.model.Categoria;
import cl.volvo.productoservice.model.Producto;
import cl.volvo.productoservice.repository.CategoriaRepository;
import cl.volvo.productoservice.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final CategoriaRepository categoriaRepo;
    private final ProductoRepository productoRepo;

    @Override
    public void run(String... args) {
        if (productoRepo.count() > 0) {
            log.info("LA BD YA TIENE DATOS, SE OMITE LA CARGA INICIAL");
            return;
        }

        Categoria accion = categoriaRepo.save(new Categoria(null, "Acción", "acc", true));
        Categoria estrategia = categoriaRepo.save(new Categoria(null, "Estrategia", "est", true));
        Categoria aventura = categoriaRepo.save(new Categoria(null, "Aventura", "avn", true));
        Categoria simulacion = categoriaRepo.save(new Categoria(null, "Simulación y deportes", "sim", true));
        Categoria terror = categoriaRepo.save(new Categoria(null, "Terror", "hor", true));


        productoRepo.saveAll(List.of(
                new Producto(null, "WFRM-01", "Warframe", 0, 100, "Shooter cooperativo gratuito en tercera persona.",
                        "/assets/images/warframe-card.jpg",
                        "https://dropreference-v3.s3.eu-west-3.amazonaws.com/prod/game-images/warframe_vertical.jpg", "Acción, Cooperativo, Multijugador", accion, true),
                new Producto(null, "TOF-02", "Tower of Fantasy: Pack Bienvenida", 9990, 100,
                        "Pack de inicio para el RPG de mundo abierto Tower of Fantasy. Incluye moneda del juego, un atuendo exclusivo y recursos para tus primeras horas en Aida. El juego base es gratuito.",
                        "/assets/images/tower-of-fantasy-card.jpg",
                        "/assets/images/tower-of-fantasy.jpg",
                        "RPG, Mundo Abierto, Multijugador", aventura, true),
                new Producto(null, "SPPL-03", "Super People: Pack Fundador", 7990, 100,
                        "Pack de fundador del battle royale Super People. Trae aspectos exclusivos y recompensas de temporada para las partidas donde cada soldado tiene habilidades especiales. El juego base es gratuito.",
                        "/assets/images/super-people-card.jpg",
                        "/assets/images/super-people.jpg",
                        "Battle Royale, Shooter, Multijugador", accion, true),
                new Producto(null, "YGMD-04", "Yu-Gi-Oh! Master Duel", 0, 100,
                        "Juego de cartas gratuito basado en Yu-Gi-Oh! Arma tu mazo con miles de cartas y compite en duelos clasificados contra jugadores de todo el mundo.",
                        "/assets/images/yugioh-master-duel-card.jpg",
                        "/assets/images/yugioh-master-duel.jpg",
                        "Cartas, Estrategia, Multijugador", estrategia, true),
                new Producto(null, "ACRD-05", "Assassin's Creed", 19990, 100,
                        "Acción y sigilo en mundo abierto. Infíltrate entre la multitud, escala ciudades históricas y sigue el conflicto entre Asesinos y Templarios en una campaña para un jugador.",
                        "https://staticctf.ubisoft.com/J3yJr34U2pZ2Ieem48Dwy9uqj5PNUQTn/449BBgnc3Q1ha2IN9rh3bR/eb9a6d17615e1480d12e9aab24804c67/ac-ezio-hero-mobile.jpg",
                        "https://staticctf.ubisoft.com/J3yJr34U2pZ2Ieem48Dwy9uqj5PNUQTn/449BBgnc3Q1ha2IN9rh3bR/eb9a6d17615e1480d12e9aab24804c67/ac-ezio-hero-mobile.jpg",
                        "Acción, Sigilo, Un Jugador", aventura, true),
                new Producto(null, "PUBG-06", "PUBG Battlegrounds", 0, 100,
                        "El battle royale que popularizó el género, hoy gratuito. Cien jugadores caen en un mapa que se va cerrando y solo uno queda en pie.",
                        "/assets/images/pubg-battlegrounds-card.jpg",
                        "/assets/images/pubg-battlegrounds.jpg",
                        "Battle Royale, Shooter, Multijugador", accion, true),
                new Producto(null, "APEX-07", "Apex Legends: Edición Campeón", 13990, 100,
                        "Edición del battle royale por escuadrones de Respawn. Desbloquea leyendas, aspectos legendarios y monedas Apex. El juego base es gratuito.",
                        "/assets/images/apex-legends-card.jpg",
                        "/assets/images/apex-legends.jpg",
                        "Battle Royale, Shooter, Multijugador", accion, true),
                new Producto(null, "SIMS-08", "The Sims 4: Expansión A Trabajar", 19990, 100,
                        "Expansión para The Sims 4. Acompaña a tus Sims a su trabajo como médico, detective o científico, o abre tu propio negocio. Requiere el juego base, que es gratuito.",
                        "/assets/images/sims4-card.jpg",
                        "/assets/images/sims4.jpg",
                        "Simulación, Vida, Un Jugador", simulacion, true),
                new Producto(null, "LOST-09", "Lost Ark: Pack Fundador", 14990, 100,
                        "Pack de fundador del RPG de acción isométrico Lost Ark. Incluye cristales, una mascota y objetos para avanzar más rápido en la búsqueda del Arca. El juego base es gratuito.",
                        "/assets/images/lost-ark-card.jpg",
                        "/assets/images/lost-ark.jpg",
                        "RPG, Acción, Multijugador", aventura, true),
                new Producto(null, "DEST-10", "Destiny 2: La Forma Final", 39990, 100,
                        "Expansión que cierra la Saga de la Luz de Destiny 2, con un destino nuevo, incursión y subclases prismáticas. Requiere el juego base, que es gratuito.",
                        "/assets/images/destiny2-card.jpg",
                        "/assets/images/destiny2.jpg",
                        "Shooter, Ciencia Ficción, Multijugador", accion, true),
                new Producto(null, "RL-11", "Rocket League", 0, 100,
                        "Fútbol con autos cohete, gratuito. Partidos de tres contra tres fáciles de entender y difíciles de dominar, con temporadas competitivas.",
                        "/assets/images/rocket-league.jpg",
                        "/assets/images/rocket-league.jpg",
                        "Deportes, Vehículos, Multijugador", simulacion, true),
                new Producto(null, "AOE4-12", "Age of Empires IV", 24990, 100,
                        "Estrategia en tiempo real. Administra recursos, levanta tu civilización y libra batallas históricas en las campañas y en partidas multijugador.",
                        "/assets/images/age-of-empires-4.jpg",
                        "/assets/images/age-of-empires-4.jpg",
                        "Estrategia, Tiempo Real, Un Jugador", estrategia, true),
                new Producto(null, "DBD-13", "Dead by Daylight", 12990, 100,
                        "Terror multijugador asimétrico. Cuatro supervivientes intentan reparar los generadores y escapar mientras un asesino controlado por otro jugador los caza.",
                        "/assets/images/dead-by-daylight.jpg",
                        "/assets/images/dead-by-daylight.jpg",
                        "Terror, Supervivencia, Multijugador", terror, true),
                new Producto(null, "BRWL-14", "Brawlhalla", 0, 100,
                        "Juego de lucha de plataformas gratuito para hasta ocho jugadores. Decenas de leyendas, partidas rápidas y modo clasificatorio.",
                        "https://shared.cloudflare.steamstatic.com/store_item_assets/steam/apps/291550/library_600x900_2x.jpg",
                        "https://shared.cloudflare.steamstatic.com/store_item_assets/steam/apps/291550/library_600x900_2x.jpg",
                        "Lucha, Plataformas, Multijugador", accion, true),
                new Producto(null, "RDR2-15", "Red Dead Redemption 2", 49990, 100,
                        "Aventura de mundo abierto en el ocaso del Lejano Oeste. Acompaña a Arthur Morgan y a la banda de Van der Linde en una campaña extensa que incluye el modo en línea Red Dead Online.",
                        "https://shared.cloudflare.steamstatic.com/store_item_assets/steam/apps/1174180/library_600x900_2x.jpg",
                        "https://shared.cloudflare.steamstatic.com/store_item_assets/steam/apps/1174180/library_600x900_2x.jpg",
                        "Acción, Mundo Abierto, Un Jugador", aventura, true),
                new Producto(null, "HK-16", "Hollow Knight", 9990, 100,
                        "Metroidvania dibujado a mano. Explora el reino en ruinas de Hallownest, mejora tu aguijón y descubre secretos en un mapa enorme e interconectado.",
                        "https://shared.cloudflare.steamstatic.com/store_item_assets/steam/apps/367520/library_600x900_2x.jpg",
                        "https://shared.cloudflare.steamstatic.com/store_item_assets/steam/apps/367520/library_600x900_2x.jpg",
                        "Metroidvania, Plataformas, Un Jugador", aventura, true),
                new Producto(null, "HKSS-17", "Hollow Knight: Silksong", 14990, 100,
                        "Secuela de Hollow Knight protagonizada por Hornet. Combate más ágil, un reino nuevo que escalar y decenas de jefes repartidos por el camino.",
                        "https://shared.cloudflare.steamstatic.com/store_item_assets/steam/apps/1030300/library_600x900_2x.jpg",
                        "https://shared.cloudflare.steamstatic.com/store_item_assets/steam/apps/1030300/library_600x900_2x.jpg",
                        "Metroidvania, Plataformas, Un Jugador", aventura, true),
                new Producto(null, "GOW-18", "God of War", 34990, 100,
                        "Kratos y su hijo Atreus cruzan las tierras de la mitología nórdica en una aventura de acción con combate cuerpo a cuerpo y una historia sobre padre e hijo.",
                        "https://shared.cloudflare.steamstatic.com/store_item_assets/steam/apps/1593500/library_600x900_2x.jpg",
                        "https://shared.cloudflare.steamstatic.com/store_item_assets/steam/apps/1593500/library_600x900_2x.jpg",
                        "Acción, Aventura, Un Jugador", accion, true),
                new Producto(null, "GOWR-19", "God of War Ragnarök", 49990, 100,
                        "Continuación directa de God of War. El Fimbulwinter anuncia el Ragnarök y Kratos y Atreus recorren los nueve reinos buscando respuestas sobre su destino.",
                        "https://shared.cloudflare.steamstatic.com/store_item_assets/steam/apps/2322010/library_600x900_2x.jpg",
                        "https://shared.cloudflare.steamstatic.com/store_item_assets/steam/apps/2322010/library_600x900_2x.jpg",
                        "Acción, Aventura, Un Jugador", accion, true),
                new Producto(null, "GTA6-20", "Grand Theft Auto VI", 69990, 100,
                        "Reserva anticipada del regreso de Rockstar a Vice City, ahora en el estado de Leonida. Sigue a Lucia y Jason en una historia criminal de mundo abierto. Al ser una reserva, el producto se activa el día del lanzamiento.",
                        "/assets/images/gta6.jpg",
                        "/assets/images/gta6.jpg",
                        "Acción, Mundo Abierto, Un Jugador", aventura, true),
                new Producto(null, "REQ-21", "Resident Evil Requiem", 59990, 100,
                        "Noveno capítulo principal de la saga de survival horror de Capcom, construido sobre el RE ENGINE. Una historia ligada al incidente de Raccoon City, con la tensión y la administración de recursos de siempre.",
                        "/assets/images/resident-evil-requiem.jpg",
                        "/assets/images/resident-evil-requiem.jpg",
                        "Terror, Supervivencia, Un Jugador", terror, true),
                new Producto(null, "DAWN-22", "The Blood of Dawnwalker", 54990, 100,
                        "RPG de acción y fantasía oscura ambientado en la Europa del siglo XIV. Encarnas a Coen, humano de día y vampiro de noche, en un mundo abierto donde el reloj corre mientras intentas salvar a tu familia.",
                        "/assets/images/blood-of-dawnwalker.jpg",
                        "/assets/images/blood-of-dawnwalker.jpg",
                        "RPG, Acción, Un Jugador", aventura, true),
                new Producto(null, "SUB2-23", "Subnautica 2", 24990, 100,
                        "Secuela de Subnautica: explora el océano de un planeta alienígena, construye tus bases y sobrevive, ahora en cooperativo con otros jugadores. Está en acceso anticipado, así que recibe contenido nuevo de forma periódica.",
                        "/assets/images/subnautica2.jpg",
                        "/assets/images/subnautica2.jpg",
                        "Supervivencia, Exploración, Cooperativo", aventura, true)
        ));


        log.info("{} CATEGORIAS Y {} PRODUCTOS INSERTADOS", categoriaRepo.count(), productoRepo.count());


    }

}
