STORYBOOK_DOCKERFILE	:= infra/docker/storybook/Dockerfile
STORYBOOK_NAME	   	 	:= smartbcity/s2-storybook
STORYBOOK_IMG	    	:= ${STORYBOOK_NAME}:${VERSION}
STORYBOOK_LATEST		:= ${STORYBOOK_NAME}:latest


clean: clean-kotlin

test: test-kotlin

package: package-kotlin package-storybook

push: push-kotlin push-storybook

push-latest: push-latest-storybook

clean-kotlin:
	./gradlew clean

package-kotlin:
	./gradlew build

push-kotlin:
	./gradlew publish -P version=${VERSION} --info

package-storybook:
	@docker build -f ${STORYBOOK_DOCKERFILE} -t ${STORYBOOK_IMG} .

push-storybook:
	@docker push ${STORYBOOK_IMG}

push-latest-storybook:
	@docker tag ${STORYBOOK_IMG} ${STORYBOOK_LATEST}
	@docker push ${STORYBOOK_LATEST}
