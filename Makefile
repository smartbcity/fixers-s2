clean: clean-kotlin

test: test-kotlin

package: package-kotlin

push: push-kotlin

clean-kotlin:
	./gradlew clean

package-kotlin:
	./gradlew build

push-kotlin:
	./gradlew publish -P version=${VERSION} --info