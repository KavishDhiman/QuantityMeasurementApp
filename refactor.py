import os
import shutil

src_dir = "src/main/java/com/bridgelabz"
dst_dir = "src/main/java/com/app/quantitymeasurement"
test_src_dir = "src/test/java/com/bridgelabz"
test_dst_dir = "src/test/java/com/app/quantitymeasurement"

if os.path.exists(src_dir):
    os.makedirs("src/main/java/com/app", exist_ok=True)
    os.rename(src_dir, dst_dir)

if os.path.exists(test_src_dir):
    os.makedirs("src/test/java/com/app", exist_ok=True)
    os.rename(test_src_dir, test_dst_dir)

for root, dirs, files in os.walk("."):
    if ".git" in root or ".idea" in root:
        continue
    for file in files:
        if file.endswith(".java") or file == "pom.xml":
            path = os.path.join(root, file)
            with open(path, "r", encoding="utf-8") as f:
                content = f.read()
            if "com.bridgelabz" in content:
                content = content.replace("com.bridgelabz", "com.app.quantitymeasurement")
                with open(path, "w", encoding="utf-8") as f:
                    f.write(content)
                print(f"Updated {path}")
