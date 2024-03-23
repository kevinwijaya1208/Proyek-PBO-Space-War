package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.*;

public class GameScreen implements Screen {
    final SpaceWar game;
    private OrthographicCamera camera;

    // Texture untuk background
    private Texture gameScreenBackground;

    // Texture untuk semua ship yang dibutuhkan
    private Texture playerShipTexture;
    private Texture enemyShip1Texture;
    private Texture enemyShip2Texture;
    private Texture enemyShip3Texture;
    private Texture bossShipTexture;

    // Texture untuk semua laser
    private Texture laserPlayerTexture;
    private Texture laserEnemy1Texture;
    private Texture laserEnemy2Texture;
    private Texture laserEnemy3Texture;
    private Texture laserBossEnemyTexture;

    // Texture untuk komponen pendukung
    private Texture asteroidTexture;
    private Texture explosionTexture;
    private Texture powerUpAttTexture;
    private Texture powerUpDefTexture;

    // Sound effect dan Music
    private Sound explosionSound;
    private Sound laserSound;
    private Music gameMusic;

    // untuk rolling background
    private int backgroundOffset;

    private SpaceShip playerShip;
    private SpaceShip bossShip;

    private ArrayList<Laser> playerLaserList;
    private ArrayList<Laser> enemyLaser1List;
    private ArrayList<Laser> enemyLaser2List;
    private ArrayList<Laser> enemyLaser3List;
    private ArrayList<Laser> bossLaserList;
    private ArrayList<Asteroid> asteroidList;
    private ArrayList<SpaceShip> enemyShipList;
    private ArrayList<Explosion> explosionList;
    private ArrayList<PowerUpAtt> powerUpAttList;
    private ArrayList<PowerUpDef> powerUpDefList;

    // variable untuk waktu
    long lastLaserPlayerTime;
    long lastLaserEnemyTime;
    long lastLaserBossTime;
    long lastAsteroidTime;
    float waktuPowerUpHabis = 0.25f;
    float waktuSebelumPowerUpHabis = 0;
    float waktuMunculPowerUp;
    float waktuSebelumPowerUp = 0;
    float waktuTulisanBossLevel = 5f;
    float waktuSebelumTulisanHilang = 0;
    float waktuGantiGerakan = 2f;
    float waktuSebelumGerak = 0;
    float waktuBossGerak = 1f;
    float waktuAntarSpawnEnemy = 3f;
    float waktuSpawnEnemy = 0;

    boolean bossLevel, getPowerUp;

    int score, destroyed, countEnemy;

    Random random;

    public GameScreen(SpaceWar game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 640);

        // background texture
        gameScreenBackground = new Texture(Gdx.files.internal("backgroundGame.png"));

        // player sama enemy ship texture
        playerShipTexture = new Texture(Gdx.files.internal("PlayerShip.png"));
        enemyShip1Texture = new Texture(Gdx.files.internal("EnemyShip1.1.png"));
        enemyShip2Texture = new Texture(Gdx.files.internal("enemyJett2-removebg-preview.png"));
        enemyShip3Texture = new Texture(Gdx.files.internal("enemyJett3.png"));
        bossShipTexture = new Texture(Gdx.files.internal("bossShip.png"));

        // laser player sama laser enemy texture
        laserPlayerTexture = new Texture(Gdx.files.internal("PlayerLaser.png"));
        laserEnemy1Texture = new Texture(Gdx.files.internal("laserGreen03.png"));
        laserEnemy2Texture = new Texture(Gdx.files.internal("laserRed16.png"));
        laserEnemy3Texture = new Texture(Gdx.files.internal("laserBlue03.png"));
        laserBossEnemyTexture = new Texture(Gdx.files.internal("bossLaserPurple.png"));

        // texture komponen pendukung
        asteroidTexture = new Texture(Gdx.files.internal("Asteroid_Brown-removebg-preview.png"));
        explosionTexture = new Texture(Gdx.files.internal("explosion.png"));
        powerUpAttTexture = new Texture(Gdx.files.internal("PowerUpLaser.png"));
        powerUpDefTexture = new Texture(Gdx.files.internal("PowerUpDef.png"));

        // Sound
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("191694__deleted-user-3544904__explosion-4.wav"));
        laserSound = Gdx.audio.newSound(Gdx.files.internal("laser.wav"));
        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("MusicGame.mp3"));
        gameMusic.setLooping(true);
        gameMusic.play();

        playerShip = new PlayerShip(400 - 32, 20, 64, 64, 20, 150, playerShipTexture, laserPlayerTexture);
        bossShip = new EnemyBossShip(400 - 64, 480, 96, 96, 40, 200, 1000, bossShipTexture, laserBossEnemyTexture);

        // inisialisasi list
        playerLaserList = new ArrayList<>();
        enemyLaser1List = new ArrayList<>();
        enemyLaser2List = new ArrayList<>();
        enemyLaser3List = new ArrayList<>();
        bossLaserList = new ArrayList<>();

        asteroidList = new ArrayList<>();
        enemyShipList = new ArrayList<>();
        explosionList = new ArrayList<>();
        powerUpAttList = new ArrayList<>();
        powerUpDefList = new ArrayList<>();

        random = new Random();
        countEnemy = 0;

        // random waktu muncul power up setiap 15-24 detik
        waktuMunculPowerUp = random.nextInt(10)+15;

    }

    @Override
    public void render(float delta) {
        game.batch.begin();

        // scrolling background
        backgroundOffset += 2;
        if (backgroundOffset % 640 == 0) {
            backgroundOffset = 0;
        }
        game.batch.draw(gameScreenBackground, 0, -backgroundOffset);
        game.batch.draw(gameScreenBackground, 0, -backgroundOffset + 640);

        // gambar player ship
        playerShip.draw(game.batch);

        renderExplosion();

        // spawn asteroid loop
        if (TimeUtils.nanoTime() - lastAsteroidTime - 900000000 - 500000000 > 1000000000) {
            spawnAsteroid();
        }

        renderAsteroid();

        // pengecekan apakah sudah boss level
        if (!bossLevel) {

            // spawn power up secara random
            spawnPowerUpRandom(delta);
//            System.out.println(waktuMunculPowerUp);
//            System.out.println(waktuSebelumPowerUp);
            renderPowerUpAtt();
            renderPowerUpDef();

            // spawn dan gambar enemy ship dengan batasan sebanyak 3
            if (countEnemy < 3)
                spawnEnemyShips(delta);


            for (Iterator<SpaceShip> iter = enemyShipList.iterator(); iter.hasNext(); ) {
                SpaceShip enemyShip = iter.next();
                enemyShip.draw(game.batch);
            }

            // random enemy move
            enemyRandomMove(delta);

            // laser player
            if (TimeUtils.nanoTime() - lastLaserPlayerTime > 1000000000 && !getPowerUp) {
                spawnLaserPlayer();
            }

            // laser player kita dapet power up att
            if(TimeUtils.nanoTime() - lastLaserPlayerTime >  1000000000 && getPowerUp) {
                if (cekPowerUp(delta)){
                    spawnLaserPlayerBuffRight();
                    spawnLaserPlayerBuffLeft();
                } else {
                    getPowerUp = false;
                    waktuSebelumPowerUpHabis = 0f;
                }
            }

            renderLaserPlayer();

            // spawn laser musuh
            if (TimeUtils.nanoTime() - lastLaserEnemyTime > 1000000000 ) {
                spawnLaserEnemy();
            }

            renderLaserEnemy();

            cekBossLevel();
        }

        // untuk boss level
        if (bossLevel) {
            waktuSebelumTulisanHilang += delta;
            if (waktuSebelumTulisanHilang < waktuTulisanBossLevel) {
                game.font.draw(game.batch, "BOSS LEVEL !!!", 300, 320);
            } else {

                // spawn power up secara random
                spawnPowerUpRandom(delta);
//                System.out.println(waktuMunculPowerUp);
//                System.out.println(waktuSebelumPowerUp);
                renderPowerUpAtt();
                renderPowerUpDef();

                // gambar dan gerakan boss
                bossShip.draw(game.batch);
                bossRandomMove(delta);

                if (TimeUtils.nanoTime() - lastLaserBossTime > 1000000000) {
                    spawnLaserBoss();
                }

                renderLaserBoss();

                if (TimeUtils.nanoTime() - lastLaserPlayerTime > 1000000000 && !getPowerUp) {
                    spawnLaserPlayer();
                }

                if (TimeUtils.nanoTime() - lastLaserPlayerTime > 1000000000 && getPowerUp){
                    if (cekPowerUp(delta)){
                        spawnLaserPlayerBuffRight();
                        spawnLaserPlayerBuffLeft();
                    } else {
                        getPowerUp = false;
                        waktuSebelumPowerUpHabis = 0f;
                    }
                }

                renderLaserPlayer();

                // ketika menang ke WinScreen
                if (bossShip.health <= 0) {
                    bossLevel = false;
                    game.setScreen(new WinScreen(game,score));
                }
            }
        }

        // ketika kalah ke GameOverScreen
        if (playerShip.health <= 0) {
            explosionSound.play();
            gameMusic.stop();
            gameMusic.dispose();
            game.setScreen(new GameOverScreen(game , score));
        }

        // info untuk score, berapa banyak ship musuh yang berhasil dihancurkan dan health dari player ship
        game.font.draw(game.batch, "Enemy Ship Destroyed: " + destroyed, 10, 590);
        game.font.draw(game.batch, "Score: " + score, 10, 620);
        game.font.draw(game.batch, "Health: " + playerShip.health, 630, 20);

        game.batch.end();

        camera.update();

        game.batch.setProjectionMatrix(camera.combined);

        // gerak pake keyboard
        ((PlayerShip)playerShip).KeyboardMove();
    }

    // Berbagai fungsi yang diperlukan di dalam class GameScreen

    // merender laser player
    private void renderLaserPlayer() {
        for (Iterator<Laser> iter = playerLaserList.iterator(); iter.hasNext(); ) {
            Laser laser = iter.next();
            laser.draw(game.batch);
            laser.laserRectangle.y += laser.movementSpeed * Gdx.graphics.getDeltaTime();
            if (bossLevel){
                if (laser.laserRectangle.overlaps(bossShip.shipRectangle)) {
                    System.out.println(laser.damage);
                    bossShip.health = bossShip.health - laser.damage;
                    iter.remove();
                    if (bossShip.health <= 0) {
                        score += ((EnemyShip)bossShip).score;
                        destroyed++;
                        explosionSound.play();
                    }
                }
            }
            if (!bossLevel) {
                for (Iterator<SpaceShip> iteratorE = enemyShipList.iterator(); iteratorE.hasNext(); ) {
                    SpaceShip enemyShip = iteratorE.next();
                    if (laser.laserRectangle.overlaps(enemyShip.shipRectangle)) {
                        enemyShip.health -= laser.damage;
                        iter.remove(); // hapus laser
                        if (enemyShip.health <= 0) {
                            Explosion explosion = new Explosion(explosionTexture, enemyShip.shipRectangle, 1f);
                            explosionList.add(explosion);
                            enemyShip.health = 0;
                            iteratorE.remove(); // hapus pesawat musuh
                            if (enemyShip instanceof EnemyShip1){
                                score+= ((EnemyShip1) enemyShip).score;
                            } else if (enemyShip instanceof  EnemyShip2){
                                score += ((EnemyShip2) enemyShip).score;
                            } else if (enemyShip instanceof EnemyShip3){
                                score += ((EnemyShip3) enemyShip).score;
                            }
                            destroyed++;
                            countEnemy--;
                            explosionSound.play();
                        }
                    }
                }
            }
            for (Iterator<Asteroid> iterator = asteroidList.iterator(); iterator.hasNext(); ) {
                Asteroid asteroid = iterator.next();
                if (laser.laserRectangle.overlaps(asteroid.asteroidRectangle)) {
                    iter.remove(); // ini hapus laser
//                    iterator.remove(); // ini hapus asteroid
                }
            }
            for (Iterator<PowerUpAtt> iterator = powerUpAttList.iterator(); iterator.hasNext(); ) {
                PowerUpAtt powerUpAtt = iterator.next();
                if(laser.laserRectangle.overlaps(powerUpAtt.powerUpRectangle)) {
                    iter.remove();
                    iterator.remove();
                    getPowerUp = true;
                    waktuSebelumPowerUpHabis = 0f;
                }
            }
            for (Iterator<PowerUpDef> iterator = powerUpDefList.iterator(); iterator.hasNext(); ) {
                PowerUpDef powerUpDef = iterator.next();
                if(laser.laserRectangle.overlaps(powerUpDef.powerUpDefRectangle)) {
                    iter.remove();
                    iterator.remove();
                    playerShip.health += 10;
                }
            }
        }
    }

    private void cekBossLevel() {
        if (score >= 2000) {
            bossLevel = true;
        }
    }

    private void renderLaserEnemy() {
        // laser enemy 1
        for (Iterator<Laser> iter = enemyLaser1List.iterator(); iter.hasNext(); ) {
            Laser laser = iter.next();
            laser.draw(game.batch);
            laser.laserRectangle.y -= laser.movementSpeed * Gdx.graphics.getDeltaTime();
            if (laser.laserRectangle.overlaps(playerShip.shipRectangle)) {
                iter.remove();
                playerShip.health -= laser.damage;
            }
        }

        // laser enemy 2
        for (Iterator<Laser> iter = enemyLaser2List.iterator(); iter.hasNext(); ) {
            Laser laser = iter.next();
            laser.draw(game.batch);
            laser.laserRectangle.y -= laser.movementSpeed * Gdx.graphics.getDeltaTime();
            if (laser.laserRectangle.overlaps(playerShip.shipRectangle)) {
                iter.remove();
                playerShip.health -= laser.damage;
            }
        }

        // laser enemy 3
        for (Iterator<Laser> iter = enemyLaser3List.iterator(); iter.hasNext(); ) {
            Laser laser = iter.next();
            laser.draw(game.batch);
            laser.laserRectangle.y -= laser.movementSpeed * Gdx.graphics.getDeltaTime();
            if (laser.laserRectangle.overlaps(playerShip.shipRectangle)) {
                iter.remove();
                playerShip.health -= laser.damage;
            }
        }
    }

    // untuk laser boss
    private void spawnLaserBoss() {
        Laser laser = new Laser(bossShip.shipRectangle.x + bossShip.shipRectangle.width / 2 - 5, bossShip.shipRectangle.y - bossShip.shipRectangle.height,
                30, 35, 5, 200, laserBossEnemyTexture);
        bossLaserList.add(laser);
        lastLaserBossTime = TimeUtils.nanoTime();
    }

    // untuk laser boss
    private void renderLaserBoss() {
        for (Iterator<Laser> iter = bossLaserList.iterator(); iter.hasNext(); ) {
            Laser laser = iter.next();
            laser.draw(game.batch);
            laser.laserRectangle.y -= laser.movementSpeed * Gdx.graphics.getDeltaTime();
            if (laser.laserRectangle.overlaps(playerShip.shipRectangle)) {
                iter.remove();
                playerShip.health -= laser.damage;
                if (playerShip.health <= 0) {
                    Explosion explosion = new Explosion(explosionTexture, playerShip.shipRectangle, 1f);
                    explosionList.add(explosion);
                    explosionSound.play();
                }
            }
        }
    }

    // mengatur waktu gerakan random dari boss
    private void bossRandomMove(float deltaTime) {
        waktuSebelumGerak += deltaTime;
        if (waktuSebelumGerak > waktuBossGerak) {
            ((EnemyBossShip) bossShip).randomMove();
            waktuSebelumGerak -= waktuBossGerak;
        } else {
            bossShip.movement(((EnemyBossShip) bossShip).directionVector.x * deltaTime * bossShip.movementSpeed,
                    ((EnemyBossShip) bossShip).directionVector.y * deltaTime * bossShip.movementSpeed);
        }
    }
    // mengatur waktu gerakan random dari enemy ship biasa
    private void enemyRandomMove(float deltaTime) {
        waktuSebelumGerak += deltaTime;
        if (waktuSebelumGerak > waktuGantiGerakan) {
            for (Iterator<SpaceShip> iter = enemyShipList.iterator(); iter.hasNext(); ) {
                SpaceShip enemyShip = iter.next();
                if (enemyShip instanceof EnemyShip1) {
                    ((EnemyShip1) enemyShip).randomMove();
                } else if (enemyShip instanceof EnemyShip2) {
                    ((EnemyShip2) enemyShip).randomMove();
                } else if (enemyShip instanceof EnemyShip3) {
                    ((EnemyShip3) enemyShip).randomMove();
                }
            }
            waktuSebelumGerak -= waktuGantiGerakan;
        } else {
            for (Iterator<SpaceShip> iter = enemyShipList.iterator(); iter.hasNext(); ) {
                SpaceShip enemyShip = iter.next();
                if (enemyShip instanceof EnemyShip1) {
                    enemyShip.movement(((EnemyShip1) enemyShip).directionVector.x * deltaTime * enemyShip.movementSpeed,
                            0);
                } else if (enemyShip instanceof EnemyShip2) {
                    enemyShip.movement(((EnemyShip2) enemyShip).directionVector.x * deltaTime * enemyShip.movementSpeed,
                            ((EnemyShip2) enemyShip).directionVector.y * deltaTime * enemyShip.movementSpeed);
                } else if (enemyShip instanceof EnemyShip3) {
                    enemyShip.movement(((EnemyShip3) enemyShip).directionVector.x * deltaTime * enemyShip.movementSpeed,
                            ((EnemyShip3) enemyShip).directionVector.y * deltaTime * enemyShip.movementSpeed);
                }
            }
        }
    }

    // spawn enemy ships secara random mulai dari enemy ship 1-3
    private void spawnEnemyShips(float deltaTime) {
        waktuSpawnEnemy += deltaTime;
        if (waktuSpawnEnemy > waktuAntarSpawnEnemy) {
            int randomEnemy = random.nextInt(3);
            if (randomEnemy == 0) {
                SpaceShip enemyShip1 = new EnemyShip1(MathUtils.random(64, 736), MathUtils.random(320, 640 - 64), 64, 64, 1, 100,100, enemyShip1Texture, laserEnemy1Texture);
                enemyShipList.add(enemyShip1);
                countEnemy++;
            } else if (randomEnemy == 1) {
                SpaceShip enemyShip2 = new EnemyShip2(MathUtils.random(64, 736), MathUtils.random(320, 640 - 64), 64, 64, 3, 100, 300, enemyShip2Texture, laserEnemy2Texture);
                enemyShipList.add(enemyShip2);
                countEnemy++;
            } else {
                SpaceShip enemyShip3 = new EnemyShip3(MathUtils.random(64, 736), MathUtils.random(320, 640 - 64), 64, 64, 5, 100, 500, enemyShip3Texture, laserEnemy3Texture);
                enemyShipList.add(enemyShip3);
                countEnemy++;
            }
            waktuSpawnEnemy -= waktuAntarSpawnEnemy;
        }
    }

    private void spawnLaserPlayer() {
        Laser laser = new Laser(playerShip.shipRectangle.x + playerShip.shipRectangle.width / 2 - 5, playerShip.shipRectangle.y + playerShip.shipRectangle.height,
                6, 20, 2, 200, laserPlayerTexture);
        playerLaserList.add(laser);
        laserSound.play();
        lastLaserPlayerTime = TimeUtils.nanoTime();
    }

    // ketika dapet power up att jadi double laser
    private void spawnLaserPlayerBuffLeft(){
        Laser laserLeft = new Laser(playerShip.shipRectangle.x + playerShip.shipRectangle.width / 2 - 20, playerShip.shipRectangle.y + playerShip.shipRectangle.height,
                6, 20, 5, 200, laserPlayerTexture);
        playerLaserList.add(laserLeft);
        laserSound.play();
        lastLaserPlayerTime = TimeUtils.nanoTime();
    }

    // ketika dapet power up att jadi double laser
    private void spawnLaserPlayerBuffRight(){
        Laser laserRight = new Laser(playerShip.shipRectangle.x + playerShip.shipRectangle.width / 2 + 15, playerShip.shipRectangle.y + playerShip.shipRectangle.height,
                6, 20, 5, 200, laserPlayerTexture);
        playerLaserList.add(laserRight);
        laserSound.play();
        lastLaserPlayerTime = TimeUtils.nanoTime();
    }

    private void spawnLaserEnemy() {
        for (Iterator<SpaceShip> iterator = enemyShipList.iterator(); iterator.hasNext(); ) {
            SpaceShip enemyShip = iterator.next();
            if (enemyShip instanceof EnemyShip1) {
                Laser laser = new Laser(enemyShip.shipRectangle.x + enemyShip.shipRectangle.width / 2 - 3, enemyShip.shipRectangle.y - enemyShip.shipRectangle.height,
                        6, 20, 1, 150, laserEnemy1Texture);
                enemyLaser1List.add(laser);
            } else if (enemyShip instanceof EnemyShip2) {
                Laser laser = new Laser(enemyShip.shipRectangle.x + enemyShip.shipRectangle.width / 2 - 3, enemyShip.shipRectangle.y - enemyShip.shipRectangle.height,
                        6, 20, 2, 150, laserEnemy2Texture);
                enemyLaser2List.add(laser);
            } else if (enemyShip instanceof EnemyShip3) {
                Laser laser = new Laser(enemyShip.shipRectangle.x + enemyShip.shipRectangle.width / 2 - 3, enemyShip.shipRectangle.y - enemyShip.shipRectangle.height,
                        6, 20, 3, 150, laserEnemy3Texture);
                enemyLaser3List.add(laser);
            }
            lastLaserEnemyTime = TimeUtils.nanoTime();
        }
    }

    // untuk spawn dan render asteroid
    private void spawnAsteroid() {
        Asteroid asteroid = new Asteroid(MathUtils.random(0f, 800-64f), 680, 64, 64, 1, 60, asteroidTexture);
        asteroidList.add(asteroid);
        lastAsteroidTime = TimeUtils.nanoTime();
    }

    // untuk spawn dan render asteroid
    private void renderAsteroid() {
        for (Iterator<Asteroid> iter = asteroidList.iterator(); iter.hasNext(); ) {
            Asteroid asteroid = iter.next();
            asteroid.draw(game.batch);
            asteroid.asteroidRectangle.y -= asteroid.movementSpeed * Gdx.graphics.getDeltaTime();
            if (asteroid.asteroidRectangle.overlaps(playerShip.shipRectangle)) {
                iter.remove();
                playerShip.health--;
                Explosion explosion = new Explosion(explosionTexture, playerShip.shipRectangle, 1f);
                explosionList.add(explosion);
                explosionSound.play();
            }
        }
    }

    // ledakan ketika pesawat musuh hancur atau pesawat player terkena asteroid
    public void renderExplosion() {
        for (Iterator<Explosion> iterator = explosionList.iterator(); iterator.hasNext(); ) {
            Explosion explosion = iterator.next();
            explosion.update(Gdx.graphics.getDeltaTime());
            if (explosion.selesai()) {
                iterator.remove();
            } else {
                explosion.draw(game.batch);
            }
        }

    }

    // untuk spawn dan render power up baik att dan def secara random
    private void spawnPowerUpRandom(float deltaTime){
        waktuSebelumPowerUp += deltaTime;
        if (waktuSebelumPowerUp > waktuMunculPowerUp){
            int randomPower = random.nextInt(2);
            if (randomPower == 0){
                spawnPowerUpAtt();
            } else {
                spawnPowerUpDef();
            }
            waktuSebelumPowerUp -= waktuMunculPowerUp;
            waktuMunculPowerUp = random.nextInt(10)+15;
        }
    }

    private void spawnPowerUpAtt() {
        PowerUpAtt powerUpAtt = new PowerUpAtt(MathUtils.random(0f, 800-64f), 680, 64, 64,  60, powerUpAttTexture);
        powerUpAttList.add(powerUpAtt);
    }

    private void spawnPowerUpDef(){
        PowerUpDef powerUpDef = new PowerUpDef(MathUtils.random(0f, 800-64f), 680, 64, 64,  60, powerUpDefTexture);
        powerUpDefList.add(powerUpDef);
    }

    private void renderPowerUpAtt(){
        for (Iterator<PowerUpAtt> iters = powerUpAttList.iterator(); iters.hasNext(); ) {
            PowerUpAtt powerUpAtt = iters.next();
            powerUpAtt.draw(game.batch);
            powerUpAtt.powerUpRectangle.y -= powerUpAtt.movementSpeed * Gdx.graphics.getDeltaTime();
            if (powerUpAtt.powerUpRectangle.overlaps(playerShip.shipRectangle)) {
                iters.remove();
            }
        }
    }

    private void renderPowerUpDef(){
        for (Iterator<PowerUpDef> iters = powerUpDefList.iterator(); iters.hasNext(); ) {
            PowerUpDef powerUpDef = iters.next();
            powerUpDef.draw(game.batch);
            powerUpDef.powerUpDefRectangle.y -= powerUpDef.movementSpeed * Gdx.graphics.getDeltaTime();
            if (powerUpDef.powerUpDefRectangle.overlaps(playerShip.shipRectangle)) {
                iters.remove();
            }
        }
    }

    // cek apakah sudah habis waktu untuk power up nya
    private boolean cekPowerUp(float deltaTime){
        waktuSebelumPowerUpHabis+= deltaTime;
//        System.out.println(waktuSebelumPowerUpHabis);
//        System.out.println(waktuPowerUpHabis);
        if (waktuSebelumPowerUpHabis < waktuPowerUpHabis){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
