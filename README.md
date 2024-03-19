Задание направлено на отработку навыка работы с многопоточностью путем разработки структуры данных для многопоточного окружения. К решению должны быть приложены модульные тесты на все значимые модули.

В данном задании следует развить решение, полученное в результате разработки утилитного метода cache, которое кэширует вызов методов, помеченных аннотацией @Cache.

Внесите в решение следующие изменения:

1)     Кэшируется результат вызова метода не только для текущего состояния объекта, но и для всех предыдущих состояний.

2)     Для кэшированных значений указывается время жизни. Если за время жизни значение не было востребовано ни разу, то оно удаляется из кэша. При востребовании значения из кэша, его срок жизни обновляется.

Разберем порядок работа этой конструкции на примере. Мы имеем интерфейс Fractionable и реализующий его класс Fraction. Обратите внимание, что два его метода помечено аннотацией @Mutator, и один аннотацией @Cache(1000). Параметр аннотации Cache задает срок жизни объектов в кэше. В данном случае это 1000 миллисекунд.