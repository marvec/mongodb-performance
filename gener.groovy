// https://mvnrepository.com/artifact/org.apache.commons/commons-lang3
@Grapes(
    @Grab(group='org.apache.commons', module='commons-lang3', version='3.5')
)

import org.apache.commons.lang3.RandomStringUtils

class Script {

  def rnd = new Random()
  def date = (new Date()).time

  def randomWord() {
     int randomStringLength = rnd.nextInt(8) + 1;
     String charset = (('a'..'z')).join()
     RandomStringUtils.random(randomStringLength, charset.toCharArray())
  }

  def randomSentence(words) {
    def s = randomWord()
    for (int i = 1; i < words; i++) { s = s + ' ' + randomWord() }
    s
  }
  
  def record() {
    def s = 'db.seconder.insert({'
    
    s = s + "\"para\" : \"${randomSentence(80)}\", "
    
    for (int i = 0; i < 20; i++) {
      s = s + "\"a$i\" : ${rnd.nextInt(Integer.MAX_VALUE)}, "
    }
    
    for (int i = 0; i < 20; i++) {
      s = s + "\"d$i\" : { \$date: ${date++} }, "
    }
    
    for (int i = 0; i < 20; i++) {
      s = s + "\"s$i\" : \"${randomSentence(10)}\", "
    }
    
    s = s + '"b" : false}, { writeConcert: { w: 1, j: true} });'
  }
}

def s = new Script()
(args[0] as int).times {
  println s.record()
}


