#
# Copyright 2017 George Aristy
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#


# Deploys binaries for full and candidate releases

POM=../pom.xml
MVN="mvn -f $POM"

# Version currently set in the POM
project_version()
{
  echo $($MVN help:evaluate -Dexpression=project.version | grep "^[0-9]")
}

# A candidate version calculated based on the current POM version and the number
# of commits since the last git tag
release_candidate_version()
{
  DESCRIBE=$(git describe)

  if [ -z "$DESCRIBE" ]; then
    RC_VERSION=$(git rev-list --count HEAD)
  else
    RC_VERSION=$(echo $DESCRIBE | cut -d - -f 2)
  fi

  actual=$(project_version)
  echo $(echo $actual | sed "s/SNAPSHOT/rc$RC_VERSION/")
}

# If the current POM version is a SNAPSHOT
is_snapshot()
{
  actual=$(project_version)
  [[ $actual =~ ^.*SNAPSHOT$ ]] && echo 1 || echo 0
}

project_version=$(project_version)
is_snapshot=$(is_snapshot)

if [ $is_snapshot == 1 ]; then
  release_version=$(release_candidate_version)
  $MVN versions:set -DnewVersion=$release_version > /dev/null
else
  release_version=$project_version
  $MVN loggit:changelog -Dloggit.startTag=$(git tag --list | tail -2 | head -1)
  $MVN releasecat:upload -Dreleasecat.token=$site_token -Dreleasecat.tag=$release_version -Dreleasecat.name=$release_version
fi

echo project version: $project_version
echo is snapshot: $is_snapshot
echo release version: $release_version

openssl aes-256-cbc -K $encrypted_af28e35ab9a0_key -iv $encrypted_af28e35ab9a0_iv -in codesigning.asc.enc -out codesigning.asc -d
gpg --fast-import codesigning.asc
$MVN --settings mvnsettings.xml -P deploy -DskipTests=true clean deploy

