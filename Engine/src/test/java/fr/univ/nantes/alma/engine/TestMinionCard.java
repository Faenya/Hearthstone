package fr.univ.nantes.alma.engine;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;
import static pl.pojo.tester.api.assertion.Method.CONSTRUCTOR;
import static pl.pojo.tester.api.assertion.Method.GETTER;
import static pl.pojo.tester.api.assertion.Method.SETTER;
import static pl.pojo.tester.api.assertion.Method.TO_STRING;

import fr.univ.nantes.alma.engine.MinionCard;

import org.junit.Test;

public class TestMinionCard {

  @Test
  public void testPojoMinionCard() {
    final Class<?> classUnderTest = MinionCard.class;

    assertPojoMethodsFor(classUnderTest)
    .testing(GETTER)
    .testing(SETTER)
    .testing(CONSTRUCTOR)
    .testing(TO_STRING)
      .areWellImplemented();
  }

  @Test
  public void testReceiveDamage() {
    MinionCard minion1 = new MinionCard(6, "common", "Chevaucheur de loup", 3, 2, 
        "Charge", 1, false, true, false, 0);
    minion1.receiveDamage(-1);
    assertThat(minion1.getHealthPoints()).isEqualTo(1);
    minion1.receiveDamage(0);
    assertThat(minion1.getHealthPoints()).isEqualTo(1);
    minion1.receiveDamage(1);
    assertThat(minion1.getHealthPoints()).isEqualTo(0);
  }
}
