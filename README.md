@Transactional은 Spring에서 간편하게 트랜잭션 처리를 할 수 있도록 도와주는 어노테이션

## 기본설정
@EnableTransactionManagement(AdivceMode.PROXY)
RollbackFor : RuntimeException, Error


## 메커니즘
- @Transactional이 적용된 경우 프록시 객체를 통해서 메서드가 실행되며, 롤백예외 발생 시 rollback처리되고, 정상 처리 또는 롤백예외가 아닌 예외 발생한 경우 commit이 실행됨


## 주의사항
기본설정인 경우,

**1. private 접근제어자**
- 프록시 객체에서 private인 경우 접근할 수 없어서 트랜잭션 처리가 불가함.

**2. 같은 클래스의 외부/내부메서드에 @Transactional을 적용한 경우**
- 외부 메서드는 프록시 객체를 통해서 실행되지만 내부 메서드는 소스코드 상의 메서드가 직접 호출됨
- 외부 메서드와 다른 트랜잭션 처리가 필요한 경우 다른 클래스로 주입해서 사용해야함

** 3. 하나의 클래스에서 내부 메서드에만 @Transactional이 적용된 경우**
- 외부 메서드가 프록시 객체를 통해 실행되지 않고, 내부 메서드 또한 프록시 객체를 통하지 않고 대상 객체가 직접 호출됨

** 4. @Transactional은 개별적인 설정으로 동작함 **
- 외부 메서드는 기본설정, 내부 메서드에는 기본설정에 rollbackFor = Exception.class를 추가한 경우 내부 메서드는 Exception 발생 시에도 전체 롤백되지만 외부메서드는 Exception 발생한 경우 COMMIT 처리됨

## 코드 실행 테스트 결과
### 테스트 구조
--try
--+-@Transactional 외부 메서드
----+-엔티티 저장
----+-@Transactional 내부 메서드(예외, 전파옵션, 외부주입)
--catch
-- 엔티티 조회 성공/실패 : COMMIT/ROLLBACK

### RuntimeException(롤백되는 예외)
REQUIRED : ROLLBACK
SUPPORTS : ROLLBACK
REQUIRES_NEW : COMMIT
NOT_SUPPORTED : COMMIT
INTERNAL_METHOD : COMMIT

### Exception(롤백 하지 않는 예외)
REQUIRED : COMMIT
SUPPORTS : COMMIT
REQUIRES_NEW : COMMIT
NOT_SUPPORTED : COMMIT
INTERNAL_METHOD : COMMIT

### CustomException extends Exception
- rollbackFor : CustomException(extends Exception)
  ROLLBACK_FOR_CUSTOM_EXCEPTION : ROLLBACK