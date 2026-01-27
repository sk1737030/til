package org.example;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Main {

  @Getter
  @AllArgsConstructor
  static class CouponDto {

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      CouponDto couponDto = (CouponDto) o;
      return Objects.equals(id, couponDto.id);
    }

    @Override
    public int hashCode() {
      return Objects.hashCode(id);
    }

    private final String id;
    private final double fixedDiscount;
    private final double percentDiscount;
    private final double minimumApplicableAmount;
    private final double maximumDiscountAmount; // 최대 할인 금액

    // 할인 금액을 계산하는 메소드
    public double calculateDiscount(double reservationAmount) {
      if (reservationAmount < minimumApplicableAmount) {
        return 0; // 최소 적용 금액을 맞지 않으면 할인을 적용할 수 없음
      }
      double discount = percentDiscount > 0 ? reservationAmount * percentDiscount / 100.0 : fixedDiscount;

      double v = reservationAmount - discount;
      if(v > 0 && v < 1000) {
        return 0;
      }

      return percentDiscount > 0 ? Math.min(discount, maximumDiscountAmount) : discount;
    }
  }

  @Getter
  static class Reservation {

    private final double amount;
    private final List<CouponDto> availableCouponDtos;
    private CouponDto appliedCouponDto;
    private double appliedDiscount;

    public Reservation(double amount, List<CouponDto> availableCouponDtos) {
      this.amount = amount;
      this.availableCouponDtos = availableCouponDtos;
    }

    public void applyCoupon(CouponDto couponDto, double discount) {
      this.appliedCouponDto = couponDto;
      this.appliedDiscount = discount;
    }

    public List<CouponDto> getAvailableCouponDtos() {
      return availableCouponDtos;
    }
  }

  @Getter
  static class Order {
    private final List<Reservation> reservations;
    private double maxTotalCouponDiscount;
    private List<CouponDto> bestCouponDtos;

    public Order(List<Reservation> reservations) {
      this.reservations = reservations;
      this.maxTotalCouponDiscount = 0;
      this.bestCouponDtos = new ArrayList<>();
    }

    public double calculateTotalDiscount() {
      int totalCombinations = 1;

      for (Reservation reservation : reservations) {
        totalCombinations *= (reservation.getAvailableCouponDtos().size() + 1);
      }

      for (int combination = 0; combination < totalCombinations; combination++) {
        List<CouponDto> currentCouponDtos = new ArrayList<>();
        double currentTotalDiscount = 0;

        int tempCombination = combination;
        boolean isValidCombination = true;
        List<String> usedCoupons = new ArrayList<>();

        for (int i = 0; i < reservations.size(); i++) {
          Reservation reservation = reservations.get(i);
          List<CouponDto> availableCouponDtos = reservation.getAvailableCouponDtos();
          int size = availableCouponDtos.size();
          int couponIndex = tempCombination % (size + 1);
          tempCombination /= (size + 1);

          CouponDto selectedCouponDto = null;

          if (couponIndex < size) {
            selectedCouponDto = availableCouponDtos.get(couponIndex);

            String id = selectedCouponDto.getId();
            if (usedCoupons.contains(id)) {
              isValidCombination = false;
              break;
            }

            double discount = selectedCouponDto.calculateDiscount(reservation.getAmount());
            if (discount > reservation.getAmount()) {
              isValidCombination = false;
              break;
            }

            if(discount == 0) {
              isValidCombination = false;
              break;
            }

            currentTotalDiscount += discount;
            usedCoupons.add(id);
          }

          currentCouponDtos.add(selectedCouponDto);
        }

//        double remainingAmount = calculateRemainingAmount(currentTotalDiscount);
        if (isValidCombination && currentTotalDiscount > maxTotalCouponDiscount /*&& remainingAmount >= 1000*/) {
          maxTotalCouponDiscount = currentTotalDiscount;
          bestCouponDtos = new ArrayList<>();
          for (int j = 0; j < reservations.size(); j++) {
            if (j < currentCouponDtos.size()) {
              bestCouponDtos.add(currentCouponDtos.get(j));
            } else {
              bestCouponDtos.add(null);
            }
          }
        }
      }

      applyBestCombination();
      return maxTotalCouponDiscount;
    }

    // 전체 예약 금액에서 총 할인 금액을 뺀 나머지 금액 계산
    private double calculateRemainingAmount(double currentTotalDiscount) {
      double totalAmount = 0;
      for (Reservation reservation : reservations) {
        totalAmount += reservation.getAmount();
      }
      return totalAmount - currentTotalDiscount;
    }


    // 최적의 쿠폰 조합을 각 예약에 적용하는 메소드
    private void applyBestCombination() {
      System.out.println("[DEBUG] Applying best combination of coupons");
      for (int i = 0; i < reservations.size(); i++) {
        Reservation reservation = reservations.get(i);
        CouponDto bestCouponDto = bestCouponDtos.get(i);
        if (bestCouponDto != null) {
          double discount = bestCouponDto.calculateDiscount(reservation.getAmount());
          reservation.applyCoupon(bestCouponDto, discount);
        } else {
          reservation.applyCoupon(null, 0);
        }
      }
    }
  }

  public static void main(String[] args) {
//    // 테스트 시나리오: 쿠폰 및 예약 정보 초기화
//    CouponDto couponDtoA = new CouponDto("1", 0, 100, 1000, 30000); // 정률 100%, 최대 30,000원
//    CouponDto couponDtoB = new CouponDto("2", 29500, 0, 1000, 0); // 정액 29,500원
//    CouponDto couponDtoC = new CouponDto("3", 10000, 0, 1000, 0); // 정액 10,000원
//
//    // 예약 A: 10,000원, 사용 가능한 쿠폰 A, B
//    List<CouponDto> reservationACouponDtos = new ArrayList<>();
//    reservationACouponDtos.add(couponDtoA);
//    reservationACouponDtos.add(couponDtoB);
//    reservationACouponDtos.add(couponDtoC);
//
//    Reservation reservationA = new Reservation(10000, reservationACouponDtos);
//
//    // 예약 B: 20,000원, 사용 가능한 쿠폰 A
//    List<CouponDto> reservationBCouponDtos = new ArrayList<>();
//    reservationBCouponDtos.add(couponDtoA);
//    reservationBCouponDtos.add(couponDtoB);
//    reservationBCouponDtos.add(couponDtoC);
//
//    Reservation reservationB = new Reservation(20000, reservationBCouponDtos);
//
//    // 예약 C: 30,000원, 사용 가능한 쿠폰 B, C
//    List<CouponDto> reservationCCouponDtos = new ArrayList<>();
//    reservationCCouponDtos.add(couponDtoA);
//    reservationCCouponDtos.add(couponDtoB);
//    reservationCCouponDtos.add(couponDtoC);
//    Reservation reservationC = new Reservation(30000, reservationCCouponDtos);
//
//    // 예약 리스트 생성 및 주문 초기화
//    List<Reservation> reservations = new ArrayList<>();
//    reservations.add(reservationA);
//    reservations.add(reservationB);
//    reservations.add(reservationC);
//
//    Order order = new Order(reservations);
//    // 총 할인 금액 계산 및 결과 출력
//    double totalDiscount = order.calculateTotalDiscount();
//    System.out.println("총 할인 금액: " + totalDiscount);
//    for (int i = 0; i < reservations.size(); i++) {
//      Reservation reservation = reservations.get(i);
//      CouponDto appliedCouponDto = reservation.getAppliedCouponDto();
//      if (appliedCouponDto != null) {
//        System.out.println("예약 " + (i + 1) + ": 쿠폰 " + appliedCouponDto.getId() + " 적용, 할인 금액: " + reservation.getAppliedDiscount());
//      } else {
//        System.out.println("예약 " + (i + 1) + ": 쿠폰 적용 없음");
//      }
//    }
//
//    // 테스트 케이스: 시나리오 2
//    System.out.println("\n테스트 케이스: 시나리오 2");
//
//
//    CouponDto couponDtoD = new CouponDto("4", 0, 100, 1000, 30000); // 정률 100%, 최대 30,000원
//    CouponDto couponDtoE = new CouponDto("5", 29000, 0, 1000, 0); // 정액 29,500원
//    CouponDto couponDtoF = new CouponDto("6", 10000, 0, 1000, 0); // 정액 10,000원
//
//    // 예약 A: 10,000원, 사용 가능한 쿠폰 D, E
//    List<CouponDto> reservationDCouponDtos = new ArrayList<>();
//    reservationDCouponDtos.add(couponDtoD);
//    reservationDCouponDtos.add(couponDtoE);
//    reservationDCouponDtos.add(couponDtoF);
//    Reservation reservationD = new Reservation(10000, reservationDCouponDtos);
//
//    // 예약 B: 20,000원, 사용 가능한 쿠폰 D
//    List<CouponDto> reservationECouponDtos = new ArrayList<>();
//    reservationECouponDtos.add(couponDtoD);
//    reservationECouponDtos.add(couponDtoE);
//    reservationECouponDtos.add(couponDtoF);
//    Reservation reservationE = new Reservation(20000, reservationECouponDtos);
//
//    // 예약 C: 30,000원, 사용 가능한 쿠폰 E, F
//    List<CouponDto> reservationFCouponDtos = new ArrayList<>();
//    reservationFCouponDtos.add(couponDtoD);
//    reservationFCouponDtos.add(couponDtoE);
//    reservationFCouponDtos.add(couponDtoF);
//    Reservation reservationF = new Reservation(30000, reservationFCouponDtos);
//
//    // 예약 리스트 생성 및 주문 초기화
//    List<Reservation> newReservations = new ArrayList<>();
//    newReservations.add(reservationD);
//    newReservations.add(reservationE);
//    newReservations.add(reservationF);
//
//    Order newOrder = new Order(newReservations);
//    // 총 할인 금액 계산 및 결과 출력
//    double newTotalDiscount = newOrder.calculateTotalDiscount();
//    System.out.println("총 할인 금액: " + newTotalDiscount);
//    for (int i = 0; i < newReservations.size(); i++) {
//      Reservation reservation = newReservations.get(i);
//      CouponDto appliedCouponDto = reservation.getAppliedCouponDto();
//      if (appliedCouponDto != null) {
//        System.out.println("예약 " + (i + 1) + ": 쿠폰 " + appliedCouponDto.getId() + " 적용, 할인 금액: " + reservation.getAppliedDiscount());
//      } else {
//        System.out.println("예약 " + (i + 1) + ": 쿠폰 적용 없음");
//      }
//    } // 테스트 케이스: 시나리오 3

    System.out.println("\n테스트 케이스: 시나리오 3");

    CouponDto couponDtoG = new CouponDto("4", 39_900, 0, 1000, 0); // 정률 100%, 최대 30,000원
    CouponDto couponDtoH = new CouponDto("5", 60_000, 0, 1000, 0); // 정액 29,500원

    // 예약 A: 10,000원, 사용 가능한 쿠폰 D, E
    List<CouponDto> reservationGCouponDtos = new ArrayList<>();
    reservationGCouponDtos.add(couponDtoG);
    reservationGCouponDtos.add(couponDtoH);
    Reservation reservationG = new Reservation(60000, reservationGCouponDtos);

    // 예약 B: 20,000원, 사용 가능한 쿠폰 D
    List<CouponDto> reservationHCouponDtos = new ArrayList<>();
    reservationHCouponDtos.add(couponDtoG);
    reservationHCouponDtos.add(couponDtoH);
    Reservation reservationH = new Reservation(40000, reservationHCouponDtos);


    // 예약 리스트 생성 및 주문 초기화
    List<Reservation> newReservationsV2 = new ArrayList<>();
    newReservationsV2.add(reservationG);
    newReservationsV2.add(reservationH);

    Order newOrderV2 = new Order(newReservationsV2);
    // 총 할인 금액 계산 및 결과 출력
    double newTotalDiscountV2 = newOrderV2.calculateTotalDiscount();
    System.out.println("총 할인 금액: " + newTotalDiscountV2);
    for (int i = 0; i < newReservationsV2.size(); i++) {
      Reservation reservation = newReservationsV2.get(i);
      CouponDto appliedCouponDto = reservation.getAppliedCouponDto();
      if (appliedCouponDto != null) {
        System.out.println("예약 " + (i + 1) + ": 쿠폰 " + appliedCouponDto.getId() + " 적용, 할인 금액: " + reservation.getAppliedDiscount());
      } else {
        System.out.println("예약 " + (i + 1) + ": 쿠폰 적용 없음");
      }
    }
  }

}