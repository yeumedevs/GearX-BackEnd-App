package com.gearx.feature.security.mail;

public class PasswordResetTemplate {
    /**
     * @param brand
     * @param otp Mã OTP 6 số
     * @param supportUrl Link hỗ trợ / liên hệ
     */
    public static String build(String brand, String username, String otp, String supportUrl) {
        String ttl = "5 phút";
        return """
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width,initial-scale=1" />
<title>%s - Mã xác thực đổi mật khẩu</title>
</head>
<body style="margin:0;padding:0;background:#F6FAFE;font-family:Arial,Helvetica,sans-serif;color:#0F172A;">
<table role="presentation" cellpadding="0" cellspacing="0" width="100%%" style="background:#F6FAFE;padding:24px 0;">
<tr>
<td align="center">
	<table role="presentation" cellpadding="0" cellspacing="0" width="600" style="max-width:600px;background:#FFFFFF;border-radius:16px;border:1px solid #E5EAF2;overflow:hidden;">

	<tr>
		<td style="padding:20px 28px;background:linear-gradient(180deg,#EAF4FF, #FFFFFF);border-bottom:1px solid #E5EAF2;">
		<table width="100%%" cellspacing="0" cellpadding="0" role="presentation">
			<tr>
			<td style="font-weight:700;font-size:18px;color:#2563EB;">%s</td>
			<td align="right" style="font-size:12px;color:#64748B;">Bảo mật tài khoản</td>
			</tr>
		</table>
		</td>
	</tr>

	<tr>
		<td style="padding:28px;">
		<h2 style="margin:0 0 10px;font-size:18px;line-height:1.3;color:#0F172A;">Mã xác thực đổi mật khẩu dành cho: %s</h2>
		<p style="margin:0 0 18px;font-size:14px;color:#334155;">
			Vui lòng dùng mã bên dưới để xác nhận yêu cầu đổi mật khẩu. Mã có hiệu lực trong <strong>%s</strong>.
		</p>

		<table role="presentation" cellpadding="0" cellspacing="0" align="center" style="margin:8px auto 20px auto;border:1px solid #E5EAF2;border-radius:28px;width:340px;background:#FFFFFF;">
			<tr>
			<td style="padding:12px 0 0 0;" align="center">
				<div style="width:56px;height:10px;background:#E5E7EB;border-radius:0 0 8px 8px;margin:0 auto 4px auto;"></div>
			</td>
			</tr>
			<tr>
			<td style="padding:18px 24px 24px 24px;" align="center">
				<div style="font-size:12px;color:#64748B;margin-bottom:8px;">MÃ OTP</div>
				<div style="display:inline-block;border:1px dashed #C7D2FE;background:#F8FAFF;border-radius:12px;padding:14px 18px;font-weight:800;font-size:28px;letter-spacing:6px;color:#1D4ED8;">
				%s
				</div>
				<div style="font-size:12px;color:#94A3B8;margin-top:10px;">
				(Nếu không hiển thị đúng, mã của bạn là: <strong>%s</strong>)
				</div>
			</td>
			</tr>
		</table>

		<p style="margin:0 0 8px;font-size:13px;color:#475569;">
			Không phải bạn yêu cầu? Có thể ai đó đã nhập nhầm email của bạn. Bạn có thể bỏ qua email này.
		</p>
		</td>
	</tr>

	<!-- Footer tối giản, tông sáng -->
	<tr>
		<td style="background:#F8FAFF;padding:14px 28px;color:#94A3B8;font-size:11px;border-top:1px solid #E5EAF2;">
		Email được gửi tự động. Vui lòng không phản hồi trực tiếp.
		</td>
	</tr>
	</table>
</td>
</tr>
</table>
</body>
</html>
"""
                .formatted(brand, brand, username, ttl, otp, otp, supportUrl, brand);
    }
}
