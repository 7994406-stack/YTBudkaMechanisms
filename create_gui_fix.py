from PIL import Image, ImageDraw
import os

# Путь сохранения
output_dir = "src/main/resources/assets/mechanisms/textures/gui"
os.makedirs(output_dir, exist_ok=True)
output_path = os.path.join(output_dir, "coal_generator_gui.png")

# Создаём изображение 256x256
img = Image.new('RGBA', (256, 256), (0, 0, 0, 0))
draw = ImageDraw.Draw(img)

# Цвета интерфейса
bg_dark = (139, 139, 139, 255)
bg_light = (198, 198, 198, 255)
slot_dark = (55, 55, 55, 255)
slot_light = (139, 139, 139, 255)

# 1. Основной фон (176x166)
draw.rectangle([0, 0, 175, 165], fill=bg_dark)
draw.rectangle([2, 2, 173, 163], fill=bg_light)
draw.rectangle([4, 4, 171, 161], fill=bg_dark)

# 2. Слот топлива (80, 53)
draw.rectangle([79, 52, 97, 70], fill=slot_dark)
draw.rectangle([80, 53, 96, 69], fill=slot_light)

# 3. Рамка энергии (152, 13)
draw.rectangle([151, 12, 164, 73], fill=slot_dark)
draw.rectangle([152, 13, 163, 72], fill=(0, 0, 0, 255))

# 4. Элементы анимации (справа от основного атласа)
# Огонь (176, 0)
draw.rectangle([176, 0, 190, 14], fill=(255, 100, 0, 255))
# Красная энергия (176, 14)
draw.rectangle([176, 14, 188, 74], fill=(255, 0, 0, 255))

# 5. Рисуем сетку инвентаря (просто для вида)
for row in range(3):
    for col in range(9):
        x, y = 8 + col * 18, 84 + row * 18
        draw.rectangle([x-1, y-1, x+17, y+17], outline=slot_dark)

# Хотбар
for col in range(9):
    x, y = 8 + col * 18, 142
    draw.rectangle([x-1, y-1, x+17, y+17], outline=slot_dark)

img.save(output_path)
print(f"DONE! Texture saved to: {output_path}")
