import setuptools

with open('README.md', 'r', encoding='UTF-8') as fd:
    long_description = fd.read()

with open('requirements.txt', 'r', encoding='UTF-8') as fd:
    requirement = fd.read().split()

setuptools.setup(
    name="admin-service",
    version="0.1",
    author="Abderrahmane ELASRI",
    author_email="el.abderrahman00@gmail.com",
    long_description=long_description,
    long_description_content_type="text/markdown",
    url="https://github.com/MountainOlympius/digital-school-platform",
    project_urls={
        "Bug Tracker": "https://github.com/MountainOlympius/digital-school-platform/issues",
    },
    install_requires=requirement,
    package_dir={"": "src"},
    packages=setuptools.find_packages(where="src"),
    python_required=">=3.6"
)
