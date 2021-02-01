package com.roulette.com.service;

import com.roulette.com.domain.Bets;
import com.roulette.com.domain.User;
import com.roulette.com.repository.BetsRepository;
import com.roulette.com.repository.UserRepository;
import com.roulette.com.service.dto.BetsDTO;
import com.roulette.com.service.mapper.BetsMapper;
import com.roulette.com.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BetsService {

    private final Logger log = LoggerFactory.getLogger(BetsService.class);

    private final BetsRepository betsRepository;

    private final BetsMapper betsMapper;

    private final UserRepository userRepository;

    private final RouletteService rouletteService;

    private final UserService userService;

    public BetsService(BetsRepository betsRepository, BetsMapper betsMapper, UserRepository userRepository, RouletteService rouletteService, UserService userService) {
        this.betsRepository = betsRepository;
        this.betsMapper = betsMapper;
        this.userRepository = userRepository;
        this.rouletteService = rouletteService;
        this.userService = userService;
    }

    public BetsDTO save(BetsDTO betsDTO) {
        log.debug("Request to save Bets : {}", betsDTO);
        Bets bets = betsMapper.toEntity(betsDTO);
        bets = betsRepository.save(bets);
        return betsMapper.toDto(bets);
    }

    public Page<BetsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Bets");
        return betsRepository.findAll(pageable)
                .map(betsMapper::toDto);
    }


    public Optional<BetsDTO> findOne(String id) {
        log.debug("Request to get Bets : {}", id);
        return betsRepository.findById(id)
                .map(betsMapper::toDto);
    }

    public void delete(String id) {
        log.debug("Request to delete Bets : {}", id);
        betsRepository.deleteById(id);
    }

    public BetsDTO openBeet(BetsDTO betsDTO, String user) {
        if (!rouletteService.findOne(betsDTO.getRoulette()).get().isState()) {
            throw new BadRequestAlertException("Roulette is not enabled\n", "BETS", "YOU_CANNOT_GET");
        }
        Optional<User> userOptional = userRepository.findOneByLogin(user);
        Integer money =  userOptional.get().getMoney();
        Integer moneyBets = betsDTO.getBetValue();
        Integer total = money - moneyBets;
        if (money >= moneyBets) {
            BetsDTO betsDTOSave = new BetsDTO();
            betsDTOSave.setUser(user);
            betsDTOSave.setBetNumber(betsDTO.getBetNumber());
            betsDTOSave.setBetValue(betsDTO.getBetValue());
            betsDTOSave.setColorBet(betsDTO.getColorBet());
            betsDTOSave.setRoulette(betsDTO.getRoulette());
            betsDTOSave.setState(true);
            userService.updateBets(total);
            return save(betsDTOSave);
        }
        return null;
    }
}
